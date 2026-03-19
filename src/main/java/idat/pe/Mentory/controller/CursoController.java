package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.AdminCursoRequestDto;
import idat.pe.Mentory.dto.CursoDto;
import idat.pe.Mentory.entity.Curso;
import idat.pe.Mentory.entity.Usuario;
import idat.pe.Mentory.mapper.CursoMapper;
import idat.pe.Mentory.service.CursoService;
import idat.pe.Mentory.service.UsuarioService;
import jakarta.validation.Valid;
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

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class CursoController {

    private final CursoService cursoService;
    private final UsuarioService usuarioService;
    private final CursoMapper cursoMapper;

    @GetMapping
    public List<CursoDto> getAll() {
        return cursoService.findAll().stream().map(cursoMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public CursoDto getById(@PathVariable Long id) {
        Curso curso = cursoService.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Curso no encontrado"));
        return cursoMapper.toDto(curso);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoDto create(@Valid @RequestBody AdminCursoRequestDto request) {
        Usuario docente = getDocenteOrThrow(request.getDocenteId());
        Curso curso = Curso.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .docente(docente)
                .estado(request.getEstado())
                .createdAt(LocalDateTime.now())
                .build();
        return cursoMapper.toDto(cursoService.save(curso));
    }

    @PutMapping("/{id}")
    public CursoDto update(@PathVariable Long id, @Valid @RequestBody AdminCursoRequestDto request) {
        Curso existing = cursoService.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Curso no encontrado"));
        Usuario docente = getDocenteOrThrow(request.getDocenteId());
        existing.setNombre(request.getNombre());
        existing.setDescripcion(request.getDescripcion());
        existing.setPrecio(request.getPrecio());
        existing.setDocente(docente);
        existing.setEstado(request.getEstado());
        return cursoMapper.toDto(cursoService.save(existing));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Curso existing = cursoService.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Curso no encontrado"));
        cursoService.deleteById(existing.getId());
    }

    private Usuario getDocenteOrThrow(Long docenteId) {
        Usuario docente = usuarioService.findByIdWithRol(docenteId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Docente no encontrado"));
        if (!"DOCENTE".equalsIgnoreCase(docente.getRol().getName())) {
            throw new ResponseStatusException(BAD_REQUEST, "El usuario asignado no tiene rol DOCENTE");
        }
        return docente;
    }
}
