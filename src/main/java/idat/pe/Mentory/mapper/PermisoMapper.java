package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.PermisoDto;
import idat.pe.Mentory.entity.Permiso;

@Mapper(componentModel = "spring")
public interface PermisoMapper {
    PermisoDto toDto(Permiso entity);

    @Mapping(target = "rolPermisos", ignore = true)
    Permiso toEntity(PermisoDto dto);
}
