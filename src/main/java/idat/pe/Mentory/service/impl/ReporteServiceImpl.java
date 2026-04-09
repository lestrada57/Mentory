package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.dto.reporte.ReporteVentasDto;
import idat.pe.Mentory.dto.reporte.VentaPorCursoDto;
import idat.pe.Mentory.repository.VentaRepository;
import idat.pe.Mentory.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final VentaRepository ventaRepository;

    @Override
    public ReporteVentasDto generarReporteVentas(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio == null) {
            inicio = LocalDateTime.of(2000, 1, 1, 0, 0);
        }
        if (fin == null) {
            fin = LocalDateTime.now();
        }

        Object[] data = (Object[]) ventaRepository.getTotalVentas(inicio, fin);
        Long cantidadVentas = 0L;
        BigDecimal ingresosTotales = BigDecimal.ZERO;

        if (data != null && data.length > 0) {
            if (data[0] != null) {
                cantidadVentas = ((Number) data[0]).longValue();
            }
            if (data.length > 1 && data[1] != null) {
                ingresosTotales = new BigDecimal(data[1].toString());
            }
        }

        List<Object[]> agrupadas = ventaRepository.getVentasAgrupadasPorCurso(inicio, fin);
        List<VentaPorCursoDto> detalle = new ArrayList<>();

        for (Object[] row : agrupadas) {
            Long cursoId = ((Number) row[0]).longValue();
            String nombreCurso = (String) row[1];
            Long cantidad = ((Number) row[2]).longValue();
            BigDecimal ingresos = row[3] != null ? new BigDecimal(row[3].toString()) : BigDecimal.ZERO;

            detalle.add(VentaPorCursoDto.builder()
                    .cursoId(cursoId)
                    .nombreCurso(nombreCurso)
                    .cantidadVentas(cantidad)
                    .ingresosTotales(ingresos)
                    .build());
        }

        return ReporteVentasDto.builder()
                .cantidadVentasTotales(cantidadVentas)
                .ingresosTotales(ingresosTotales)
                .detallePorCurso(detalle)
                .build();
    }
}
