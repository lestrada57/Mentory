package idat.pe.Mentory.service;

import idat.pe.Mentory.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();

    List<Usuario> findAdminManageableUsers();

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByIdWithRol(Long id);

    Usuario save(Usuario usuario);

    void deleteById(Long id);
}
