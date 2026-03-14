package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Entrega;

import java.util.List;
import java.util.Optional;

public interface EntregaService {
    List<Entrega> findAll();

    Optional<Entrega> findById(Long id);

    Entrega save(Entrega entrega);

    void deleteById(Long id);
}
