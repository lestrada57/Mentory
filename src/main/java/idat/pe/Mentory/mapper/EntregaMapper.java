package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.EntregaDto;
import idat.pe.Mentory.entity.Entrega;
import idat.pe.Mentory.entity.Tarea;
import idat.pe.Mentory.entity.Usuario;

@Mapper(componentModel = "spring")
public interface EntregaMapper {
    @Mapping(target = "tareaId", source = "tarea.id")
    @Mapping(target = "estudianteId", source = "estudiante.id")
    EntregaDto toDto(Entrega entity);

    @Mapping(target = "tarea", source = "tareaId")
    @Mapping(target = "estudiante", source = "estudianteId")
    Entrega toEntity(EntregaDto dto);

    default Tarea mapTarea(Long id) {
        if (id == null) {
            return null;
        }
        Tarea tarea = new Tarea();
        tarea.setId(id);
        return tarea;
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
