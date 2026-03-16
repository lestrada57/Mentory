package idat.pe.Mentory.repository;

import idat.pe.Mentory.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    Optional<Permiso> findByCodeIgnoreCase(String code);
}
