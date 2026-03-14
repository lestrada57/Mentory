package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.AuditoriaDto;
import idat.pe.Mentory.entity.Auditoria;
import idat.pe.Mentory.entity.Usuario;

@Mapper(componentModel = "spring")
public interface AuditoriaMapper {
    @Mapping(target = "usuarioId", source = "usuario.id")
    AuditoriaDto toDto(Auditoria entity);

    @Mapping(target = "usuario", source = "usuarioId")
    Auditoria toEntity(AuditoriaDto dto);

    default Usuario mapUsuario(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
