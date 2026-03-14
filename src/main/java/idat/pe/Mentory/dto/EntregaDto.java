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
public class EntregaDto {
    private Long id;
    private Long tareaId;
    private Long estudianteId;
    private String archivoUrl;
    private LocalDateTime fechaEntrega;
    private BigDecimal nota;
    private String retroalimentacion;
}
