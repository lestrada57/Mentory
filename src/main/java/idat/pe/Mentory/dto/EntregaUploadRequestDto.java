package idat.pe.Mentory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EntregaUploadRequestDto {

    @NotNull(message = "El ID de la tarea es obligatorio")
    private Long tareaId;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;
}

