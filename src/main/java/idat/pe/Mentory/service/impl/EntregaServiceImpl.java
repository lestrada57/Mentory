package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Entrega;
import idat.pe.Mentory.repository.EntregaRepository;
import idat.pe.Mentory.repository.TareaRepository;
import idat.pe.Mentory.repository.UsuarioRepository;
import idat.pe.Mentory.service.EntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntregaServiceImpl implements EntregaService {

    private final EntregaRepository entregaRepository;
    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Entrega> findAll() {
        return entregaRepository.findAll();
    }

    @Override
    public Optional<Entrega> findById(Long id) {
        return entregaRepository.findById(id);
    }

    @Override
    public Entrega save(Entrega entrega) {
        if (entrega.getTarea() == null || entrega.getTarea().getId() == null) {
            throw new IllegalArgumentException("tareaId es obligatorio para guardar entrega");
        }
        if (entrega.getEstudiante() == null || entrega.getEstudiante().getId() == null) {
            throw new IllegalArgumentException("estudianteId es obligatorio para guardar entrega");
        }
        tareaRepository.findById(entrega.getTarea().getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe la tarea asociada a la entrega"));
        usuarioRepository.findById(entrega.getEstudiante().getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el estudiante asociado a la entrega"));
        return entregaRepository.save(entrega);
    }

    @Override
    public void deleteById(Long id) {
        entregaRepository.deleteById(id);
    }
}
