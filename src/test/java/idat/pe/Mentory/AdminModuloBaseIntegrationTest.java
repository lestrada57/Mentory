package idat.pe.Mentory;

import idat.pe.Mentory.entity.Rol;
import idat.pe.Mentory.entity.Usuario;
import idat.pe.Mentory.repository.RolRepository;
import idat.pe.Mentory.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminModuloBaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void adminGestionaUsuariosCursosYSesiones() throws Exception {
        String suffix = String.valueOf(System.nanoTime());
        Rol rolAdmin = rolRepository.findByNameIgnoreCase("ADMINISTRADOR").orElseThrow();
        Rol rolDocente = rolRepository.findByNameIgnoreCase("DOCENTE").orElseThrow();
        Rol rolEstudiante = rolRepository.findByNameIgnoreCase("ESTUDIANTE").orElseThrow();

        Usuario admin = usuarioRepository.save(Usuario.builder()
                .nombres("Admin")
                .apellidos("Mentory")
                .email("admin." + suffix + "@mentory.com")
                .password(passwordEncoder.encode("admin123"))
                .rol(rolAdmin)
                .activo(true)
                .createdAt(LocalDateTime.now())
                .build());

        Usuario docente = usuarioRepository.save(Usuario.builder()
                .nombres("Docente")
                .apellidos("Uno")
                .email("docente." + suffix + "@mentory.com")
                .password(passwordEncoder.encode("docente123"))
                .rol(rolDocente)
                .activo(true)
                .createdAt(LocalDateTime.now())
                .build());

        Usuario estudiante = usuarioRepository.save(Usuario.builder()
                .nombres("Estudiante")
                .apellidos("Uno")
                .email("estudiante." + suffix + "@mentory.com")
                .password(passwordEncoder.encode("estudiante123"))
                .rol(rolEstudiante)
                .activo(true)
                .createdAt(LocalDateTime.now())
                .build());

        String loginBody = """
                {
                  "email":"%s",
                  "password":"admin123"
                }
                """.formatted(admin.getEmail());

        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();

        String token = extractStringField(loginResult.getResponse().getContentAsString(), "token");

        mockMvc.perform(get("/api/usuarios")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        String updateUsuarioBody = """
                {
                  "nombres":"Docente Actualizado",
                  "apellidos":"Principal",
                  "email":"docente.edit.%s@mentory.com",
                  "rolId":%d
                }
                """.formatted(suffix, rolDocente.getId());

        mockMvc.perform(put("/api/usuarios/{id}", docente.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUsuarioBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(docente.getId()))
                .andExpect(jsonPath("$.rol").value("DOCENTE"));

        mockMvc.perform(patch("/api/usuarios/{id}/bloquear", estudiante.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(false));

        mockMvc.perform(patch("/api/usuarios/{id}/activar", estudiante.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(true));

        String createCursoBody = """
                {
                  "nombre":"Java Backend %s",
                  "descripcion":"Curso de backend",
                  "precio":350.00,
                  "docenteId":%d,
                  "estado":"PUBLICADO"
                }
                """.formatted(suffix, docente.getId());

        MvcResult createCursoResult = mockMvc.perform(post("/api/cursos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCursoBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn();

        Long cursoId = extractLongField(createCursoResult.getResponse().getContentAsString(), "id");

        String createSesionBody = """
                {
                  "titulo":"Sesión Intro",
                  "fecha":"2026-03-20T19:00:00",
                  "enlaceZoom":"https://zoom.us/j/123456789"
                }
                """;

        mockMvc.perform(post("/api/cursos/{cursoId}/sesiones", cursoId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createSesionBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cursoId").value(cursoId))
                .andExpect(jsonPath("$.enlaceZoom").value("https://zoom.us/j/123456789"));

        mockMvc.perform(get("/api/cursos/{cursoId}/sesiones", cursoId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    private String extractStringField(String json, String fieldName) {
        Pattern pattern = Pattern.compile("\"" + fieldName + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new IllegalStateException("No se pudo extraer el campo " + fieldName);
        }
        return matcher.group(1);
    }

    private Long extractLongField(String json, String fieldName) {
        Pattern pattern = Pattern.compile("\"" + fieldName + "\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new IllegalStateException("No se pudo extraer el campo " + fieldName);
        }
        return Long.parseLong(matcher.group(1));
    }
}
