package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Auditoria;

import java.util.List;
import java.util.Optional;

public interface AuditoriaService {
    List<Auditoria> findAll();

    Optional<Auditoria> findById(Long id);

    Auditoria save(Auditoria auditoria);

    void deleteById(Long id);
}
