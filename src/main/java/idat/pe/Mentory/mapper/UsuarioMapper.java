package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.UsuarioDto;
import idat.pe.Mentory.entity.Rol;
import idat.pe.Mentory.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "rolId", source = "rol.id")
    UsuarioDto toDto(Usuario entity);

    @Mapping(target = "rol", source = "rolId")
    @Mapping(target = "cursosDocente", ignore = true)
    @Mapping(target = "inscripciones", ignore = true)
    @Mapping(target = "entregas", ignore = true)
    @Mapping(target = "asistencias", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    @Mapping(target = "auditorias", ignore = true)
    Usuario toEntity(UsuarioDto dto);

    default Rol mapRol(Long id) {
        if (id == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setId(id);
        return rol;
    }
}
