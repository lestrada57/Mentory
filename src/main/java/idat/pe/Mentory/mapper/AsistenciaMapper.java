package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.AsistenciaDto;
import idat.pe.Mentory.entity.Asistencia;
import idat.pe.Mentory.entity.Sesion;
import idat.pe.Mentory.entity.Usuario;

@Mapper(componentModel = "spring")
public interface AsistenciaMapper {
    @Mapping(target = "sesionId", source = "sesion.id")
    @Mapping(target = "estudianteId", source = "estudiante.id")
    AsistenciaDto toDto(Asistencia entity);

    @Mapping(target = "sesion", source = "sesionId")
    @Mapping(target = "estudiante", source = "estudianteId")
    Asistencia toEntity(AsistenciaDto dto);

    default Sesion mapSesion(Long id) {
        if (id == null) {
            return null;
        }
        Sesion sesion = new Sesion();
        sesion.setId(id);
        return sesion;
    }

    default Usuario mapUsuario(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
