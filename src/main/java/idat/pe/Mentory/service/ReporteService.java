package idat.pe.Mentory.service;

import idat.pe.Mentory.dto.reporte.ReporteVentasDto;
import java.time.LocalDateTime;

public interface ReporteService {
    ReporteVentasDto generarReporteVentas(LocalDateTime inicio, LocalDateTime fin);
}
