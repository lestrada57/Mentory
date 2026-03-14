package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.SesionDto;
import idat.pe.Mentory.entity.Curso;
import idat.pe.Mentory.entity.Sesion;

@Mapper(componentModel = "spring")
public interface SesionMapper {
    @Mapping(target = "cursoId", source = "curso.id")
    SesionDto toDto(Sesion entity);

    @Mapping(target = "curso", source = "cursoId")
    @Mapping(target = "asistencias", ignore = true)
    @Mapping(target = "tareas", ignore = true)
    Sesion toEntity(SesionDto dto);

    default Curso mapCurso(Long id) {
        if (id == null) {
            return null;
        }
        Curso curso = new Curso();
        curso.setId(id);
        return curso;
    }
}
