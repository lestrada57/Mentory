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
public class PagoDto {
    private Long id;
    private Long inscripcionId;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String metodoPago;
    private Boolean confirmado;
}
