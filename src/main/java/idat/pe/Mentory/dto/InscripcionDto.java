package idat.pe.Mentory.dto;

import java.math.BigDecimal;
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
public class InscripcionDto {
    private Long id;
    private Long estudianteId;
    private Long cursoId;
    private String estado;
    private BigDecimal porcentajeAsistencia;
    private LocalDateTime createdAt;
}
