package idat.pe.Mentory.service;

import idat.pe.Mentory.dto.AuthResponseDto;
import idat.pe.Mentory.dto.LoginRequestDto;
import idat.pe.Mentory.dto.SignUpRequestDto;
import idat.pe.Mentory.dto.UsuarioResponseDto;

public interface AuthService {
    UsuarioResponseDto signUp(SignUpRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}
