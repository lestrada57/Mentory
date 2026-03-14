package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Permiso;

import java.util.List;
import java.util.Optional;

public interface PermisoService {
    List<Permiso> findAll();

    Optional<Permiso> findById(Long id);

    Permiso save(Permiso permiso);

    void deleteById(Long id);
}
