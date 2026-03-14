package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.InscripcionDto;
import idat.pe.Mentory.entity.Curso;
import idat.pe.Mentory.entity.Inscripcion;
import idat.pe.Mentory.entity.Usuario;

@Mapper(componentModel = "spring")
public interface InscripcionMapper {
    @Mapping(target = "estudianteId", source = "estudiante.id")
    @Mapping(target = "cursoId", source = "curso.id")
    InscripcionDto toDto(Inscripcion entity);

    @Mapping(target = "estudiante", source = "estudianteId")
    @Mapping(target = "curso", source = "cursoId")
    @Mapping(target = "pagos", ignore = true)
    @Mapping(target = "ventas", ignore = true)
    Inscripcion toEntity(InscripcionDto dto);

    default Usuario mapUsuario(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    default Curso mapCurso(Long id) {
        if (id == null) {
            return null;
        }
        Curso curso = new Curso();
        curso.setId(id);
        return curso;
    }
}
