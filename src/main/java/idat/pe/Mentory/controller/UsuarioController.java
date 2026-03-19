package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.AdminUsuarioUpdateRequestDto;
import idat.pe.Mentory.dto.UsuarioResponseDto;
import idat.pe.Mentory.entity.Rol;
import idat.pe.Mentory.entity.Usuario;
import idat.pe.Mentory.repository.RolRepository;
import idat.pe.Mentory.repository.UsuarioRepository;
import idat.pe.Mentory.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @GetMapping
    public List<UsuarioResponseDto> getAllManageable() {
        return usuarioService.findAdminManageableUsers().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDto getById(@PathVariable Long id) {
        Usuario usuario = getManageableById(id);
        return toResponse(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDto update(@PathVariable Long id, @Valid @RequestBody AdminUsuarioUpdateRequestDto request) {
        Usuario existing = getManageableById(id);
        String normalizedEmail = request.getEmail().trim().toLowerCase();
        if (usuarioRepository.existsByEmailIgnoreCase(normalizedEmail)
                && !existing.getEmail().equalsIgnoreCase(normalizedEmail)) {
            throw new ResponseStatusException(BAD_REQUEST, "El correo ya está registrado");
        }
        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Rol no encontrado"));
        validateManageableRole(rol.getName());
        existing.setNombres(request.getNombres());
        existing.setApellidos(request.getApellidos());
        existing.setEmail(normalizedEmail);
        existing.setRol(rol);
        return toResponse(saveAndReloadWithRol(existing));
    }

    @PatchMapping("/{id}/bloquear")
    public UsuarioResponseDto bloquear(@PathVariable Long id) {
        Usuario existing = getManageableById(id);
        existing.setActivo(false);
        return toResponse(saveAndReloadWithRol(existing));
    }

    @PatchMapping("/{id}/activar")
    public UsuarioResponseDto activar(@PathVariable Long id) {
        Usuario existing = getManageableById(id);
        existing.setActivo(true);
        return toResponse(saveAndReloadWithRol(existing));
    }

    private Usuario getManageableById(Long id) {
        Usuario usuario = usuarioService.findByIdWithRol(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));
        validateManageableRole(usuario.getRol().getName());
        return usuario;
    }

    private void validateManageableRole(String roleName) {
        if (!"DOCENTE".equalsIgnoreCase(roleName) && !"ESTUDIANTE".equalsIgnoreCase(roleName)) {
            throw new ResponseStatusException(BAD_REQUEST, "Solo se gestionan usuarios docentes y estudiantes");
        }
    }

    private Usuario saveAndReloadWithRol(Usuario usuario) {
        Usuario saved = usuarioService.save(usuario);
        return usuarioService.findByIdWithRol(saved.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado tras actualizar"));
    }

    private UsuarioResponseDto toResponse(Usuario usuario) {
        return UsuarioResponseDto.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .rolId(usuario.getRol().getId())
                .rol(usuario.getRol().getName())
                .activo(usuario.getActivo())
                .createdAt(usuario.getCreatedAt())
                .build();
    }
}
