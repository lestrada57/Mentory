package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.SaludoResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class SaludoController {

    @GetMapping("/saludo")
    public SaludoResponseDto saludo() {
        return SaludoResponseDto.builder()
                .mensaje("Saludo desde la API")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .version("1.0")
                .build();
    }
}
