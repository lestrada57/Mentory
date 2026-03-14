package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.TareaDto;
import idat.pe.Mentory.entity.Sesion;
import idat.pe.Mentory.entity.Tarea;

@Mapper(componentModel = "spring")
public interface TareaMapper {
    @Mapping(target = "sesionId", source = "sesion.id")
    TareaDto toDto(Tarea entity);

    @Mapping(target = "sesion", source = "sesionId")
    @Mapping(target = "entregas", ignore = true)
    Tarea toEntity(TareaDto dto);

    default Sesion mapSesion(Long id) {
        if (id == null) {
            return null;
        }
        Sesion sesion = new Sesion();
        sesion.setId(id);
        return sesion;
    }
}
