package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Pago;

import java.util.List;
import java.util.Optional;

public interface PagoService {
    List<Pago> findAll();

    Optional<Pago> findById(Long id);

    Pago save(Pago pago);

    void deleteById(Long id);
}
