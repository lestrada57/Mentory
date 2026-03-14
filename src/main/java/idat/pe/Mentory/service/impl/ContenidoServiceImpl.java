package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Contenido;
import idat.pe.Mentory.repository.ContenidoRepository;
import idat.pe.Mentory.service.ContenidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContenidoServiceImpl implements ContenidoService {

    private final ContenidoRepository contenidoRepository;

    @Override
    public List<Contenido> findAll() {
        return contenidoRepository.findAll();
    }

    @Override
    public Optional<Contenido> findById(Long id) {
        return contenidoRepository.findById(id);
    }

    @Override
    public Contenido save(Contenido contenido) {
        return contenidoRepository.save(contenido);
    }

    @Override
    public void deleteById(Long id) {
        contenidoRepository.deleteById(id);
    }
}
