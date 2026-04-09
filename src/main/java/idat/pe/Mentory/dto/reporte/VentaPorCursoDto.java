package idat.pe.Mentory.dto.reporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaPorCursoDto {
    private Long cursoId;
    private String nombreCurso;
    private Long cantidadVentas;
    private BigDecimal ingresosTotales;
}
