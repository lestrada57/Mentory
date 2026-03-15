package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Tarea;
import idat.pe.Mentory.repository.CursoRepository;
import idat.pe.Mentory.repository.TareaRepository;
import idat.pe.Mentory.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final CursoRepository cursoRepository;

    @Override
    public List<Tarea> findAll() {
        return tareaRepository.findAll();
    }

    @Override
    public Optional<Tarea> findById(Long id) {
        return tareaRepository.findById(id);
    }

    @Override
    public Tarea save(Tarea tarea) {
        if (tarea.getCurso() == null || tarea.getCurso().getId() == null) {
            throw new IllegalArgumentException("cursoId es obligatorio para guardar tarea");
        }
        cursoRepository.findById(tarea.getCurso().getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el curso asociado a la tarea"));
        return tareaRepository.save(tarea);
    }

    @Override
    public void deleteById(Long id) {
        tareaRepository.deleteById(id);
    }
}
