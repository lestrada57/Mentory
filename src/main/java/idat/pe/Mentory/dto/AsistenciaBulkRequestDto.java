package idat.pe.Mentory.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AsistenciaBulkRequestDto {

    @NotNull
    private Long sesionId;

    @Valid
    @NotNull
    private List<RegistroAsistenciaDto> registros;

    @Data
    public static class RegistroAsistenciaDto {
        @NotNull
        private Long estudianteId;

        @NotNull
        private String estado;
    }
}

