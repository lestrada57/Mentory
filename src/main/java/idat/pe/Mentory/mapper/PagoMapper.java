package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.PagoDto;
import idat.pe.Mentory.entity.Inscripcion;
import idat.pe.Mentory.entity.Pago;

@Mapper(componentModel = "spring")
public interface PagoMapper {
    @Mapping(target = "inscripcionId", source = "inscripcion.id")
    PagoDto toDto(Pago entity);

    @Mapping(target = "inscripcion", source = "inscripcionId")
    Pago toEntity(PagoDto dto);

    default Inscripcion mapInscripcion(Long id) {
        if (id == null) {
            return null;
        }
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(id);
        return inscripcion;
    }
}
