package idat.pe.Mentory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ContenidoUploadRequestDto {
    @NotNull
    private Long cursoId;

    @NotBlank
    private String titulo;

    private String descripcion;

    private String tipo;

    private String estado;
}
