package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.NotificacionDto;
import idat.pe.Mentory.entity.Notificacion;
import idat.pe.Mentory.entity.Usuario;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {
    @Mapping(target = "usuarioId", source = "usuario.id")
    NotificacionDto toDto(Notificacion entity);

    @Mapping(target = "usuario", source = "usuarioId")
    Notificacion toEntity(NotificacionDto dto);

    default Usuario mapUsuario(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
