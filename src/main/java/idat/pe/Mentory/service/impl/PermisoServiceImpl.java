package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Permiso;
import idat.pe.Mentory.repository.PermisoRepository;
import idat.pe.Mentory.service.PermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;

    @Override
    public List<Permiso> findAll() {
        return permisoRepository.findAll();
    }

    @Override
    public Optional<Permiso> findById(Long id) {
        return permisoRepository.findById(id);
    }

    @Override
    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    @Override
    public void deleteById(Long id) {
        permisoRepository.deleteById(id);
    }
}
