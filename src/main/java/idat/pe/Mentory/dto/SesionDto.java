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
public class SesionDto {
    private Long id;
    private Long cursoId;
    private String titulo;
    private LocalDateTime fecha;
    private String enlaceZoom;
}
