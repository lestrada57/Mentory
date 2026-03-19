package idat.pe.Mentory.controller;

import idat.pe.Mentory.dto.EntregaDto;
import idat.pe.Mentory.dto.EntregaUploadRequestDto;
import idat.pe.Mentory.entity.Entrega;
import idat.pe.Mentory.entity.Tarea;
import idat.pe.Mentory.entity.Usuario;
import idat.pe.Mentory.mapper.EntregaMapper;
import idat.pe.Mentory.repository.EntregaRepository;
import idat.pe.Mentory.repository.TareaRepository;
import idat.pe.Mentory.repository.UsuarioRepository;
import idat.pe.Mentory.service.MinioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/entregas")
@RequiredArgsConstructor
public class EntregaController {

    private final EntregaRepository entregaRepository;
    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntregaMapper entregaMapper;
    private final MinioService minioService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public EntregaDto uploadEntrega(
            @RequestPart("file") MultipartFile file,
            @Valid @RequestPart("entrega") EntregaUploadRequestDto request
    ) {
        Tarea tarea = tareaRepository.findById(request.getTareaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada"));

        Usuario estudiante = usuarioRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

        String objectKey = minioService.uploadFile(file, "entregas/tareas/" + tarea.getId());

        Entrega entrega = Entrega.builder()
                .tarea(tarea)
                .estudiante(estudiante)
                .archivoKey(objectKey)
                .nombreArchivo(file.getOriginalFilename())
                .pesoBytes(file.getSize())
                .formato(resolveFormato(file.getOriginalFilename()))
                .fechaEntrega(LocalDateTime.now())
                .build();

        return entregaMapper.toDto(entregaRepository.save(entrega));
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE', 'ESTUDIANTE')")
    public Map<String, String> downloadEntrega(@PathVariable Long id) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrega no encontrada"));

        if (entrega.getArchivoKey() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La entrega no tiene un archivo asociado");
        }

        String downloadUrl = minioService.getPresignedUrl(entrega.getArchivoKey());
        return Map.of("downloadUrl", downloadUrl);
    }

    private String resolveFormato(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "desconocido";
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}

