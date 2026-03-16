package idat.pe.Mentory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDto {
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private Long rolId;
    private String rol;
    private Boolean activo;
    private LocalDateTime createdAt;
}
