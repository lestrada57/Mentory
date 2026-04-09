package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.AsistenciaBulkRequestDto;
import idat.pe.Mentory.service.AsistenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sesiones")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    @PostMapping("/{sesionId}/asistencias/bulk")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Void> registrarAsistenciaMasiva(
            @PathVariable Long sesionId,
            @Valid @RequestBody AsistenciaBulkRequestDto request) {

        if (!sesionId.equals(request.getSesionId())) {
            return ResponseEntity.badRequest().build();
        }

        asistenciaService.registrarAsistenciaMasiva(sesionId, request.getRegistros());
        return ResponseEntity.ok().build();
    }
}

