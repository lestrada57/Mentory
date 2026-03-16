package idat.pe.Mentory.security;

import idat.pe.Mentory.entity.RolPermiso;
import idat.pe.Mentory.entity.Usuario;
import idat.pe.Mentory.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailWithAuthorities(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + normalizeAuthority(usuario.getRol().getName())));
        for (RolPermiso rolPermiso : usuario.getRol().getRolPermisos()) {
            authorities.add(new SimpleGrantedAuthority(normalizeAuthority(rolPermiso.getPermiso().getCode())));
        }

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .disabled(!Boolean.TRUE.equals(usuario.getActivo()))
                .authorities(authorities)
                .build();
    }

    private String normalizeAuthority(String value) {
        return value.trim().toUpperCase().replace(" ", "_").replace("-", "_");
    }
}
