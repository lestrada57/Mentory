package idat.pe.Mentory.repository;

import idat.pe.Mentory.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);
}
