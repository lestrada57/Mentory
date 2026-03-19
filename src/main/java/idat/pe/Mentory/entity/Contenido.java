package idat.pe.Mentory.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contenidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "curso_id", nullable = false, insertable = false, updatable = false)
    private Long cursoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "archivo_key", columnDefinition = "TEXT")
    private String archivoKey;

    @Column(name = "nombre_archivo", length = 255)
    private String nombreArchivo;

    @Column(name = "peso_bytes")
    private Long pesoBytes;

    @Column(name = "formato", length = 50)
    private String formato;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
