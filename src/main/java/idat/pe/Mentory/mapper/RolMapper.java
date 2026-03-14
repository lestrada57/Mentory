package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.RolDto;
import idat.pe.Mentory.entity.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {
    RolDto toDto(Rol entity);

    @Mapping(target = "rolPermisos", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    Rol toEntity(RolDto dto);
}
