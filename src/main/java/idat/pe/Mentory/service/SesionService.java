package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Sesion;

import java.util.List;
import java.util.Optional;

public interface SesionService {
    List<Sesion> findAll();

    List<Sesion> findAllByCursoId(Long cursoId);

    Optional<Sesion> findById(Long id);

    boolean existsByIdAndCursoId(Long id, Long cursoId);

    Sesion save(Sesion sesion);

    void deleteById(Long id);
}
