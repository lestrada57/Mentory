package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.AdminSesionRequestDto;
import idat.pe.Mentory.dto.SesionDto;
import idat.pe.Mentory.entity.Curso;
import idat.pe.Mentory.entity.Sesion;
import idat.pe.Mentory.mapper.SesionMapper;
import idat.pe.Mentory.service.CursoService;
import idat.pe.Mentory.service.SesionService;
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

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/cursos/{cursoId}/sesiones")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class SesionController {

    private final SesionService sesionService;
    private final CursoService cursoService;
    private final SesionMapper sesionMapper;

    @GetMapping
    public List<SesionDto> getByCurso(@PathVariable Long cursoId) {
        ensureCursoExists(cursoId);
        return sesionService.findAllByCursoId(cursoId).stream().map(sesionMapper::toDto).toList();
    }

    @GetMapping("/{sesionId}")
    public SesionDto getById(@PathVariable Long cursoId, @PathVariable Long sesionId) {
        ensureCursoExists(cursoId);
        Sesion sesion = getSesionOfCurso(cursoId, sesionId);
        return sesionMapper.toDto(sesion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SesionDto create(@PathVariable Long cursoId, @Valid @RequestBody AdminSesionRequestDto request) {
        Curso curso = ensureCursoExists(cursoId);
        Sesion sesion = Sesion.builder()
                .curso(curso)
                .titulo(request.getTitulo())
                .fecha(request.getFecha())
                .enlaceZoom(request.getEnlaceZoom())
                .build();
        return sesionMapper.toDto(sesionService.save(sesion));
    }

    @PutMapping("/{sesionId}")
    public SesionDto update(@PathVariable Long cursoId, @PathVariable Long sesionId, @Valid @RequestBody AdminSesionRequestDto request) {
        ensureCursoExists(cursoId);
        Sesion existing = getSesionOfCurso(cursoId, sesionId);
        existing.setTitulo(request.getTitulo());
        existing.setFecha(request.getFecha());
        existing.setEnlaceZoom(request.getEnlaceZoom());
        return sesionMapper.toDto(sesionService.save(existing));
    }

    @DeleteMapping("/{sesionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cursoId, @PathVariable Long sesionId) {
        ensureCursoExists(cursoId);
        Sesion existing = getSesionOfCurso(cursoId, sesionId);
        sesionService.deleteById(existing.getId());
    }

    private Curso ensureCursoExists(Long cursoId) {
        return cursoService.findById(cursoId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Curso no encontrado"));
    }

    private Sesion getSesionOfCurso(Long cursoId, Long sesionId) {
        Sesion sesion = sesionService.findById(sesionId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Sesión no encontrada"));
        if (!sesionService.existsByIdAndCursoId(sesion.getId(), cursoId)) {
            throw new ResponseStatusException(NOT_FOUND, "Sesión no encontrada en el curso");
        }
        return sesion;
    }
}
