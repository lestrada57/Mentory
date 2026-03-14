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
public class CursoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Long docenteId;
    private String estado;
    private LocalDateTime createdAt;
}
