package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Contenido;

import java.util.List;
import java.util.Optional;

public interface ContenidoService {
    List<Contenido> findAll();

    Optional<Contenido> findById(Long id);

    Contenido save(Contenido contenido);

    void deleteById(Long id);
}
