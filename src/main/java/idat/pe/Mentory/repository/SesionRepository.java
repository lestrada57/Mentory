package idat.pe.Mentory.repository;

import idat.pe.Mentory.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findAllByCursoIdOrderByFechaAsc(Long cursoId);

    boolean existsByIdAndCursoId(Long id, Long cursoId);

    int countByCursoId(Long cursoId);
}
