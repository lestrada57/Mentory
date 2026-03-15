package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.CursoDto;
import idat.pe.Mentory.entity.Curso;
import idat.pe.Mentory.entity.Usuario;

@Mapper(componentModel = "spring")
public interface CursoMapper {
    @Mapping(target = "docenteId", source = "docente.id")
    CursoDto toDto(Curso entity);

    @Mapping(target = "docente", source = "docenteId")
    @Mapping(target = "sesiones", ignore = true)
    @Mapping(target = "inscripciones", ignore = true)
    @Mapping(target = "contenidos", ignore = true)
    @Mapping(target = "tareas", ignore = true)
    Curso toEntity(CursoDto dto);

    default Usuario mapUsuario(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
