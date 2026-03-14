package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaService {
    List<Tarea> findAll();

    Optional<Tarea> findById(Long id);

    Tarea save(Tarea tarea);

    void deleteById(Long id);
}
