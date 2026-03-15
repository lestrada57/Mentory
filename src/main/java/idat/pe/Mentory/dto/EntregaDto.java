package idat.pe.Mentory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    private Long tareaId;
    @NotNull
    private Long estudianteId;
    private String archivoKey;
    @Size(max = 255)
    private String nombreArchivo;
    private Long pesoBytes;
    @Size(max = 50)
    private String formato;
    private LocalDateTime fechaEntrega;
    private BigDecimal nota;
    private String retroalimentacion;
}
