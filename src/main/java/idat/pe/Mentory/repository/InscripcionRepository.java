package idat.pe.Mentory.repository;

import idat.pe.Mentory.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);

    Optional<Inscripcion> findByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);
}
