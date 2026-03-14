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
public class TareaDto {
    private Long id;
    private Long sesionId;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaLimite;
    private LocalDateTime createdAt;
}
