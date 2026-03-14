package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.RolPermiso;
import idat.pe.Mentory.repository.RolPermisoRepository;
import idat.pe.Mentory.service.RolPermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolPermisoServiceImpl implements RolPermisoService {

    private final RolPermisoRepository rolPermisoRepository;

    @Override
    public List<RolPermiso> findAll() {
        return rolPermisoRepository.findAll();
    }

    @Override
    public Optional<RolPermiso> findById(Long id) {
        return rolPermisoRepository.findById(id);
    }

    @Override
    public RolPermiso save(RolPermiso rolPermiso) {
        return rolPermisoRepository.save(rolPermiso);
    }

    @Override
    public void deleteById(Long id) {
        rolPermisoRepository.deleteById(id);
    }
}
