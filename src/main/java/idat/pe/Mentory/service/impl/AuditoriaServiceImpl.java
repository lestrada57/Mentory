package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Auditoria;
import idat.pe.Mentory.repository.AuditoriaRepository;
import idat.pe.Mentory.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    @Override
    public List<Auditoria> findAll() {
        return auditoriaRepository.findAll();
    }

    @Override
    public Optional<Auditoria> findById(Long id) {
        return auditoriaRepository.findById(id);
    }

    @Override
    public Auditoria save(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }

    @Override
    public void deleteById(Long id) {
        auditoriaRepository.deleteById(id);
    }
}
