package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Pago;
import idat.pe.Mentory.repository.InscripcionRepository;
import idat.pe.Mentory.repository.PagoRepository;
import idat.pe.Mentory.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final InscripcionRepository inscripcionRepository;

    @Override
    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    @Override
    public Optional<Pago> findById(Long id) {
        return pagoRepository.findById(id);
    }

    @Override
    @Transactional
    public Pago save(Pago pago) {
        if (pago.getInscripcion() == null || pago.getInscripcion().getId() == null) {
            throw new IllegalArgumentException("inscripcionId es obligatorio para guardar pago");
        }
        var inscripcion = inscripcionRepository.findById(pago.getInscripcion().getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe la inscripción asociada al pago"));
        if (Boolean.TRUE.equals(pago.getConfirmado())) {
            inscripcion.setEstado("PAGADO");
            inscripcionRepository.save(inscripcion);
        }
        return pagoRepository.save(pago);
    }

    @Override
    public void deleteById(Long id) {
        pagoRepository.deleteById(id);
    }
}
