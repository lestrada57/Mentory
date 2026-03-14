package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Sesion;

import java.util.List;
import java.util.Optional;

public interface SesionService {
    List<Sesion> findAll();

    Optional<Sesion> findById(Long id);

    Sesion save(Sesion sesion);

    void deleteById(Long id);
}
