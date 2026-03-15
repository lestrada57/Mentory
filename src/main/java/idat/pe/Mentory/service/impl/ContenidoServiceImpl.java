package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Contenido;
import idat.pe.Mentory.repository.ContenidoRepository;
import idat.pe.Mentory.repository.CursoRepository;
import idat.pe.Mentory.service.ContenidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContenidoServiceImpl implements ContenidoService {

    private final ContenidoRepository contenidoRepository;
    private final CursoRepository cursoRepository;

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
        if (contenido.getCurso() == null || contenido.getCurso().getId() == null) {
            throw new IllegalArgumentException("cursoId es obligatorio para guardar contenido");
        }
        cursoRepository.findById(contenido.getCurso().getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el curso asociado al contenido"));
        return contenidoRepository.save(contenido);
    }

    @Override
    public void deleteById(Long id) {
        contenidoRepository.deleteById(id);
    }
}
