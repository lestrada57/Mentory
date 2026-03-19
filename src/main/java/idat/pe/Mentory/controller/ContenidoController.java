package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.ContenidoDto;
import idat.pe.Mentory.dto.ContenidoUploadRequestDto;
import idat.pe.Mentory.entity.Contenido;
import idat.pe.Mentory.entity.Curso;
import idat.pe.Mentory.mapper.ContenidoMapper;
import idat.pe.Mentory.repository.ContenidoRepository;
import idat.pe.Mentory.repository.CursoRepository;
import idat.pe.Mentory.entity.Usuario;
import idat.pe.Mentory.repository.InscripcionRepository;
import idat.pe.Mentory.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import idat.pe.Mentory.service.MinioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/contenidos")
@RequiredArgsConstructor
public class ContenidoController {

    private final ContenidoRepository contenidoRepository;
    private final CursoRepository cursoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContenidoMapper contenidoMapper;
    private final MinioService minioService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ContenidoDto uploadContenido(
            @RequestPart("file") MultipartFile file,
            @Valid @RequestPart("contenido") ContenidoUploadRequestDto request
    ) {
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Curso no encontrado"));

        String objectKey = minioService.uploadFile(file, "contenidos/cursos/" + curso.getId());

        Contenido contenido = Contenido.builder()
                .curso(curso)
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .tipo(request.getTipo())
                .estado(request.getEstado())
                .archivoKey(objectKey)
                .nombreArchivo(file.getOriginalFilename())
                .pesoBytes(file.getSize())
                .formato(resolveFormato(file.getOriginalFilename()))
                .fechaPublicacion(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        return contenidoMapper.toDto(contenidoRepository.save(contenido));
    }

    private String resolveFormato(String filename) {
        if (filename == null || !filename.contains(".")) {
            return null;
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE', 'ESTUDIANTE')")
    public Map<String, String> downloadContenido(@PathVariable Long id) {
        Contenido contenido = contenidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contenido no encontrado"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
        }
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));

        if ("ESTUDIANTE".equalsIgnoreCase(usuario.getRol().getName())) {
            if (!inscripcionRepository.existsByEstudianteIdAndCursoId(usuario.getId(), contenido.getCursoId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No estás inscrito en este curso");
            }
        }

        if (contenido.getArchivoKey() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El contenido no tiene un archivo asociado");
        }

        String downloadUrl = minioService.getPresignedUrl(contenido.getArchivoKey());
        return Map.of("downloadUrl", downloadUrl);
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEstadoContenido(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }

        if (!java.util.List.of("PUBLICADO", "OCULTO", "PROGRAMADO").contains(nuevoEstado.toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido. Valores permitidos: PUBLICADO, OCULTO, PROGRAMADO");
        }

        Contenido contenido = contenidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contenido no encontrado"));

        contenido.setEstado(nuevoEstado.toUpperCase());
        contenidoRepository.save(contenido);
    }
}
