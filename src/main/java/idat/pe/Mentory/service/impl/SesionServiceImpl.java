package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Sesion;
import idat.pe.Mentory.repository.SesionRepository;
import idat.pe.Mentory.service.SesionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SesionServiceImpl implements SesionService {

    private final SesionRepository sesionRepository;

    @Override
    public List<Sesion> findAll() {
        return sesionRepository.findAll();
    }

    @Override
    public Optional<Sesion> findById(Long id) {
        return sesionRepository.findById(id);
    }

    @Override
    public Sesion save(Sesion sesion) {
        return sesionRepository.save(sesion);
    }

    @Override
    public void deleteById(Long id) {
        sesionRepository.deleteById(id);
    }
}
