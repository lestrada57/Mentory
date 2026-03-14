package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Notificacion;
import idat.pe.Mentory.repository.NotificacionRepository;
import idat.pe.Mentory.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Override
    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    @Override
    public Optional<Notificacion> findById(Long id) {
        return notificacionRepository.findById(id);
    }

    @Override
    public Notificacion save(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    public void deleteById(Long id) {
        notificacionRepository.deleteById(id);
    }
}
