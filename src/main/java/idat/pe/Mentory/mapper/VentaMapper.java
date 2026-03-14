package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.VentaDto;
import idat.pe.Mentory.entity.Inscripcion;
import idat.pe.Mentory.entity.Venta;

@Mapper(componentModel = "spring")
public interface VentaMapper {
    @Mapping(target = "inscripcionId", source = "inscripcion.id")
    VentaDto toDto(Venta entity);

    @Mapping(target = "inscripcion", source = "inscripcionId")
    Venta toEntity(VentaDto dto);

    default Inscripcion mapInscripcion(Long id) {
        if (id == null) {
            return null;
        }
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(id);
        return inscripcion;
    }
}
