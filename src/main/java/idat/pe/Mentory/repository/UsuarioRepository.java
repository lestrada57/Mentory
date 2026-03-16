package idat.pe.Mentory.repository;

import idat.pe.Mentory.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailIgnoreCase(String email);

    Optional<Usuario> findByEmailIgnoreCase(String email);

    @Query("""
            select distinct u
            from Usuario u
            left join fetch u.rol r
            left join fetch r.rolPermisos rp
            left join fetch rp.permiso
            where lower(u.email) = lower(:email)
            """)
    Optional<Usuario> findByEmailWithAuthorities(@Param("email") String email);
}
