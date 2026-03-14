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
public class UsuarioDto {
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private Long rolId;
    private Boolean activo;
    private LocalDateTime createdAt;
}
