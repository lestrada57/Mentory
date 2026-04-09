package idat.pe.Mentory.dto.reporte;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ReporteVentasDto {
    private BigDecimal ingresosTotales;
    private Long cantidadVentasTotales;
    private List<VentaPorCursoDto> detallePorCurso;
}

