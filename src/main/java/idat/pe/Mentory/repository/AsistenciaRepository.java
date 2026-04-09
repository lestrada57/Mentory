package idat.pe.Mentory.repository;

import idat.pe.Mentory.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    Optional<Asistencia> findBySesionIdAndEstudianteId(Long sesionId, Long estudianteId);

    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.sesion.curso.id = :cursoId AND a.estudiante.id = :estudianteId AND a.estado IN :estados")
    int countByCursoIdAndEstudianteIdAndEstadoIn(@Param("cursoId") Long cursoId, @Param("estudianteId") Long estudianteId, @Param("estados") List<String> estados);
}
