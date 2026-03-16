package idat.pe.Mentory.config;

import idat.pe.Mentory.entity.Rol;
import idat.pe.Mentory.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class RoleDataInitializer {

    private final RolRepository rolRepository;

    @Bean
    CommandLineRunner initializeRoles() {
        return args -> {
            createRoleIfMissing("ADMINISTRADOR", "Acceso total al sistema");
            createRoleIfMissing("DOCENTE", "Gestión académica de cursos asignados");
            createRoleIfMissing("ESTUDIANTE", "Acceso al aula virtual y cursos inscritos");
        };
    }

    private void createRoleIfMissing(String name, String description) {
        if (rolRepository.findByNameIgnoreCase(name).isEmpty()) {
            rolRepository.save(Rol.builder()
                    .name(name)
                    .description(description)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
    }
}
