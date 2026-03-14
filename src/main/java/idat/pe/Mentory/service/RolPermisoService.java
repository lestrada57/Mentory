package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.RolPermiso;

import java.util.List;
import java.util.Optional;

public interface RolPermisoService {
    List<RolPermiso> findAll();

    Optional<RolPermiso> findById(Long id);

    RolPermiso save(RolPermiso rolPermiso);

    void deleteById(Long id);
}
