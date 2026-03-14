package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.ContenidoDto;
import idat.pe.Mentory.entity.Contenido;
import idat.pe.Mentory.entity.Curso;

@Mapper(componentModel = "spring")
public interface ContenidoMapper {
    @Mapping(target = "cursoId", source = "curso.id")
    ContenidoDto toDto(Contenido entity);

    @Mapping(target = "curso", source = "cursoId")
    Contenido toEntity(ContenidoDto dto);

    default Curso mapCurso(Long id) {
        if (id == null) {
            return null;
        }
        Curso curso = new Curso();
        curso.setId(id);
        return curso;
    }
}
