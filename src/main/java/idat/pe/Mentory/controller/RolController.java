package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.RolDto;
import idat.pe.Mentory.entity.Rol;
import idat.pe.Mentory.mapper.RolMapper;
import idat.pe.Mentory.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class RolController {

    private final RolService rolService;
    private final RolMapper rolMapper;

    @GetMapping
    public List<RolDto> getAll() {
        return rolService.findAll().stream().map(rolMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public RolDto getById(@PathVariable Long id) {
        Rol rol = rolService.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Rol no encontrado"));
        return rolMapper.toDto(rol);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RolDto create(@RequestBody RolDto dto) {
        Rol rol = rolMapper.toEntity(dto);
        rol.setId(null);
        if (rol.getCreatedAt() == null) {
            rol.setCreatedAt(LocalDateTime.now());
        }
        return rolMapper.toDto(rolService.save(rol));
    }

    @PutMapping("/{id}")
    public RolDto update(@PathVariable Long id, @RequestBody RolDto dto) {
        Rol existing = rolService.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Rol no encontrado"));
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        return rolMapper.toDto(rolService.save(existing));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Rol existing = rolService.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Rol no encontrado"));
        rolService.deleteById(existing.getId());
    }
}
