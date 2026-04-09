package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.entity.Asistencia;
import idat.pe.Mentory.dto.AsistenciaBulkRequestDto;
import idat.pe.Mentory.repository.AsistenciaRepository;
import idat.pe.Mentory.repository.SesionRepository;
import idat.pe.Mentory.repository.InscripcionRepository;
import idat.pe.Mentory.repository.NotificacionRepository;
import idat.pe.Mentory.entity.Sesion;
import idat.pe.Mentory.entity.Usuario;
import idat.pe.Mentory.entity.Inscripcion;
import idat.pe.Mentory.entity.Notificacion;
import idat.pe.Mentory.service.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final SesionRepository sesionRepository;
    private final InscripcionRepository inscripcionRepository;
    private final NotificacionRepository notificacionRepository;

    @Override
    public List<Asistencia> findAll() {
        return asistenciaRepository.findAll();
    }

    @Override
    public Optional<Asistencia> findById(Long id) {
        return asistenciaRepository.findById(id);
    }

    @Override
    public Asistencia save(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    @Override
    public void deleteById(Long id) {
        asistenciaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void registrarAsistenciaMasiva(Long sesionId, List<AsistenciaBulkRequestDto.RegistroAsistenciaDto> registros) {
        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Sesión no encontrada"));

        Long cursoId = sesion.getCurso().getId();
        int totalSesiones = sesionRepository.countByCursoId(cursoId);

        if (totalSesiones == 0) {
            return;
        }

        for (AsistenciaBulkRequestDto.RegistroAsistenciaDto registro : registros) {
            Asistencia asistencia = asistenciaRepository.findBySesionIdAndEstudianteId(sesionId, registro.getEstudianteId())
                    .orElseGet(() -> {
                        Asistencia nueva = new Asistencia();
                        nueva.setSesion(sesion);
                        Usuario estudiante = new Usuario();
                        estudiante.setId(registro.getEstudianteId());
                        nueva.setEstudiante(estudiante);
                        return nueva;
                    });

            asistencia.setEstado(registro.getEstado());
            asistenciaRepository.save(asistencia);

            int asistenciasValidas = asistenciaRepository.countByCursoIdAndEstudianteIdAndEstadoIn(
                    cursoId, registro.getEstudianteId(), Arrays.asList("PRESENTE", "JUSTIFICADO"));

            double porcentaje = ((double) asistenciasValidas / totalSesiones) * 100.0;

            Inscripcion inscripcion = inscripcionRepository.findByEstudianteIdAndCursoId(registro.getEstudianteId(), cursoId)
                    .orElse(null);

            if (inscripcion != null) {
                inscripcion.setPorcentajeAsistencia(BigDecimal.valueOf(porcentaje));
                inscripcionRepository.save(inscripcion);

                if (porcentaje < 70.0) {
                    Notificacion notificacion = Notificacion.builder()
                            .usuario(inscripcion.getEstudiante())
                            .titulo("Alerta de Asistencia")
                            .mensaje("Tu porcentaje de asistencia en el curso " + sesion.getCurso().getNombre() + " es menor al 70%. (" + String.format("%.2f", porcentaje) + "%)")
                            .leido(false)
                            .fecha(LocalDateTime.now())
                            .build();
                    notificacionRepository.save(notificacion);
                }
            }
        }
    }
}
