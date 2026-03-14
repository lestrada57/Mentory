package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Inscripcion;

import java.util.List;
import java.util.Optional;

public interface InscripcionService {
    List<Inscripcion> findAll();

    Optional<Inscripcion> findById(Long id);

    Inscripcion save(Inscripcion inscripcion);

    void deleteById(Long id);
}
