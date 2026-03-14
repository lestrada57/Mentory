package idat.pe.Mentory.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditoriaDto {
    private Long id;
    private Long usuarioId;
    private String accion;
    private String entidad;
    private Long entidadId;
    private LocalDateTime fecha;
}
