package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Entrega;
import idat.pe.Mentory.repository.EntregaRepository;
import idat.pe.Mentory.service.EntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntregaServiceImpl implements EntregaService {

    private final EntregaRepository entregaRepository;

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
        return entregaRepository.save(entrega);
    }

    @Override
    public void deleteById(Long id) {
        entregaRepository.deleteById(id);
    }
}
