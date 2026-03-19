package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.AdminCursoRequestDto;
import idat.pe.Mentory.dto.CursoDto;
import idat.pe.Mentory.entity.Curso;
import idat.pe.Mentory.entity.Usuario;
import idat.pe.Mentory.mapper.CursoMapper;
import idat.pe.Mentory.service.CursoService;
import idat.pe.Mentory.service.UsuarioService;
import idat.pe.Mentory.repository.CursoRepository;
import idat.pe.Mentory.service.MinioService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class CursoController {

    private final CursoService cursoService;
    private final CursoRepository cursoRepository; // Inyectado
    private final MinioService minioService;
    private final UsuarioService usuarioService;
    private final CursoMapper cursoMapper;

    @PostMapping(value = "/{id}/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> uploadImagen(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Curso no encontrado"));

        String imageKey = minioService.uploadFile(file, "cursos/portadas");
        curso.setImagenKey(imageKey);
        cursoRepository.save(curso);

        return Map.of("imagenKey", imageKey, "url", minioService.getPresignedUrl(imageKey));
    }

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
