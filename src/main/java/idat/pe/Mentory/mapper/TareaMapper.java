package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.TareaDto;
import idat.pe.Mentory.entity.Curso;
import idat.pe.Mentory.entity.Tarea;

@Mapper(componentModel = "spring")
public interface TareaMapper {
    @Mapping(target = "cursoId", source = "curso.id")
    TareaDto toDto(Tarea entity);

    @Mapping(target = "curso", source = "cursoId")
    @Mapping(target = "entregas", ignore = true)
    Tarea toEntity(TareaDto dto);

    default Curso mapCurso(Long id) {
        if (id == null) {
            return null;
        }
        Curso curso = new Curso();
        curso.setId(id);
        return curso;
    }
}
