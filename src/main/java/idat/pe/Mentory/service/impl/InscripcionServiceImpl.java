package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Inscripcion;
import idat.pe.Mentory.repository.CursoRepository;
import idat.pe.Mentory.repository.InscripcionRepository;
import idat.pe.Mentory.repository.UsuarioRepository;
import idat.pe.Mentory.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    @Override
    public List<Inscripcion> findAll() {
        return inscripcionRepository.findAll();
    }

    @Override
    public Optional<Inscripcion> findById(Long id) {
        return inscripcionRepository.findById(id);
    }

    @Override
    public Inscripcion save(Inscripcion inscripcion) {
        if (inscripcion.getEstudiante() == null || inscripcion.getEstudiante().getId() == null) {
            throw new IllegalArgumentException("estudianteId es obligatorio para guardar inscripción");
        }
        if (inscripcion.getCurso() == null || inscripcion.getCurso().getId() == null) {
            throw new IllegalArgumentException("cursoId es obligatorio para guardar inscripción");
        }
        usuarioRepository.findById(inscripcion.getEstudiante().getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el estudiante asociado a la inscripción"));
        cursoRepository.findById(inscripcion.getCurso().getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el curso asociado a la inscripción"));
        if (inscripcion.getId() == null
                && inscripcionRepository.existsByEstudianteIdAndCursoId(
                        inscripcion.getEstudiante().getId(), inscripcion.getCurso().getId())) {
            throw new IllegalArgumentException("El estudiante ya tiene una inscripción activa para este curso");
        }
        if (inscripcion.getEstado() == null || inscripcion.getEstado().isBlank()) {
            inscripcion.setEstado("PREINSCRITO");
        }
        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public void deleteById(Long id) {
        inscripcionRepository.deleteById(id);
    }
}
