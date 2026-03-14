package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Inscripcion;
import idat.pe.Mentory.repository.InscripcionRepository;
import idat.pe.Mentory.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    @Override
    public List<Inscripcion> findAll() {
        return inscripcionRepository.findAll();
    }

    @Override
    public Optional<Inscripcion> findById(Long id) {
        return inscripcionRepository.findById(id);
    }

    @Override
    public Inscripcion save(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public void deleteById(Long id) {
        inscripcionRepository.deleteById(id);
    }
}
