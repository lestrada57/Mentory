package idat.pe.Mentory.dto;

import jakarta.validation.constraints.NotNull;
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
public class AdminSesionRequestDto {
    private String titulo;

    @NotNull
    private LocalDateTime fecha;

    private String enlaceZoom;
}
