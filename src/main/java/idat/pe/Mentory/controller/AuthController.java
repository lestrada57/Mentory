package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.AuthResponseDto;
import idat.pe.Mentory.dto.LoginRequestDto;
import idat.pe.Mentory.dto.SignUpRequestDto;
import idat.pe.Mentory.dto.UsuarioResponseDto;
import idat.pe.Mentory.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDto signUp(@Valid @RequestBody SignUpRequestDto request) {
        return authService.signUp(request);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        return authService.login(request);
    }
}
