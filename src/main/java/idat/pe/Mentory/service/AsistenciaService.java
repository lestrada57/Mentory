package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Asistencia;

import java.util.List;
import java.util.Optional;

public interface AsistenciaService {
    List<Asistencia> findAll();

    Optional<Asistencia> findById(Long id);

    Asistencia save(Asistencia asistencia);

    void deleteById(Long id);
}
