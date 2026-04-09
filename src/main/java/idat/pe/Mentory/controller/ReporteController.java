package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.reporte.ReporteVentasDto;
import idat.pe.Mentory.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas")
    public ReporteVentasDto getReporteVentas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        return reporteService.generarReporteVentas(
                inicio != null ? inicio.atStartOfDay() : null,
                fin != null ? fin.atTime(23, 59, 59) : null
        );
    }
}
