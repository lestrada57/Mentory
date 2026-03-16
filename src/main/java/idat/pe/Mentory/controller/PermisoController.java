package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.PermisoDto;
import idat.pe.Mentory.entity.Permiso;
import idat.pe.Mentory.mapper.PermisoMapper;
import idat.pe.Mentory.service.PermisoService;
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

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class PermisoController {

    private final PermisoService permisoService;
    private final PermisoMapper permisoMapper;

    @GetMapping
    public List<PermisoDto> getAll() {
        return permisoService.findAll().stream().map(permisoMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public PermisoDto getById(@PathVariable Long id) {
        Permiso permiso = permisoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Permiso no encontrado"));
        return permisoMapper.toDto(permiso);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermisoDto create(@RequestBody PermisoDto dto) {
        Permiso permiso = permisoMapper.toEntity(dto);
        permiso.setId(null);
        return permisoMapper.toDto(permisoService.save(permiso));
    }

    @PutMapping("/{id}")
    public PermisoDto update(@PathVariable Long id, @RequestBody PermisoDto dto) {
        Permiso existing = permisoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Permiso no encontrado"));
        existing.setName(dto.getName());
        existing.setCode(dto.getCode());
        return permisoMapper.toDto(permisoService.save(existing));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Permiso existing = permisoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Permiso no encontrado"));
        permisoService.deleteById(existing.getId());
    }
}
