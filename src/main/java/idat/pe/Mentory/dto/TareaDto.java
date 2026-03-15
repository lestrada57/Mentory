package idat.pe.Mentory.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
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
public class TareaDto {
    private Long id;
    @NotNull
    private Long cursoId;
    @NotBlank
    @Size(max = 150)
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaLimite;
    private LocalDateTime createdAt;
}
