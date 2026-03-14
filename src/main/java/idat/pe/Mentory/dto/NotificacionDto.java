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
public class NotificacionDto {
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String mensaje;
    private Boolean leido;
    private LocalDateTime fecha;
}
