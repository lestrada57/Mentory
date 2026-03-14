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
public class ContenidoDto {
    private Long id;
    private Long cursoId;
    private String titulo;
    private String descripcion;
    private String tipo;
    private String urlArchivo;
    private String estado;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime createdAt;
}
