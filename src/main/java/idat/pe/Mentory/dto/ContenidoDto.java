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
public class ContenidoDto {
    private Long id;
    @NotNull
    private Long cursoId;
    @NotBlank
    @Size(max = 150)
    private String titulo;
    private String descripcion;
    private String tipo;
    private String archivoKey;
    @Size(max = 255)
    private String nombreArchivo;
    private Long pesoBytes;
    @Size(max = 50)
    private String formato;
    private String estado;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime createdAt;
}
