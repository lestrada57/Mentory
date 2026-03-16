package idat.pe.Mentory.service.impl;

import idat.pe.Mentory.dto.AuthResponseDto;
import idat.pe.Mentory.dto.LoginRequestDto;
import idat.pe.Mentory.dto.SignUpRequestDto;
import idat.pe.Mentory.dto.UsuarioResponseDto;
import idat.pe.Mentory.entity.Rol;
import idat.pe.Mentory.entity.Usuario;
import idat.pe.Mentory.repository.RolRepository;
import idat.pe.Mentory.repository.UsuarioRepository;
import idat.pe.Mentory.security.JwtService;
import idat.pe.Mentory.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public UsuarioResponseDto signUp(SignUpRequestDto request) {
        if (usuarioRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "El correo ya está registrado");
        }

        Rol rol = resolveRoleForSignUp(request.getRolId());

        Usuario usuario = Usuario.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(rol)
                .activo(true)
                .createdAt(LocalDateTime.now())
                .build();

        Usuario saved = usuarioRepository.save(usuario);
        return UsuarioResponseDto.builder()
                .id(saved.getId())
                .nombres(saved.getNombres())
                .apellidos(saved.getApellidos())
                .email(saved.getEmail())
                .rolId(saved.getRol().getId())
                .rol(saved.getRol().getName())
                .activo(saved.getActivo())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception ex) {
            throw new ResponseStatusException(UNAUTHORIZED, "Credenciales inválidas");
        }

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtService.generateToken(usuario.getId(), usuario.getRol().getName(), userDetails);

        return AuthResponseDto.builder()
                .token(token)
                .userId(usuario.getId())
                .email(usuario.getEmail())
                .rol(usuario.getRol().getName())
                .build();
    }

    private Rol resolveRoleForSignUp(Long rolId) {
        if (rolId != null) {
            return rolRepository.findById(rolId)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Rol no encontrado"));
        }
        return rolRepository.findByNameIgnoreCase("ESTUDIANTE")
                .or(() -> rolRepository.findByNameIgnoreCase("Estudiante"))
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "No existe rol base ESTUDIANTE"));
    }
}
