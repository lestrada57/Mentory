package idat.pe.Mentory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import idat.pe.Mentory.dto.RolPermisoDto;
import idat.pe.Mentory.entity.Permiso;
import idat.pe.Mentory.entity.Rol;
import idat.pe.Mentory.entity.RolPermiso;

@Mapper(componentModel = "spring")
public interface RolPermisoMapper {
    @Mapping(target = "rolId", source = "rol.id")
    @Mapping(target = "permisoId", source = "permiso.id")
    RolPermisoDto toDto(RolPermiso entity);

    @Mapping(target = "rol", source = "rolId")
    @Mapping(target = "permiso", source = "permisoId")
    RolPermiso toEntity(RolPermisoDto dto);

    default Rol mapRol(Long id) {
        if (id == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setId(id);
        return rol;
    }

    default Permiso mapPermiso(Long id) {
        if (id == null) {
            return null;
        }
        Permiso permiso = new Permiso();
        permiso.setId(id);
        return permiso;
    }
}
