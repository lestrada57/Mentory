package idat.pe.Mentory.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "docente_id", nullable = false)
    private Usuario docente;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "curso")
    @Builder.Default
    private Set<Sesion> sesiones = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @Builder.Default
    private Set<Inscripcion> inscripciones = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @Builder.Default
    private Set<Contenido> contenidos = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @Builder.Default
    private Set<Tarea> tareas = new HashSet<>();
}
