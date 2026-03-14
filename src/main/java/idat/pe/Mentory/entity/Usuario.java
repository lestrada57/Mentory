package idat.pe.Mentory.entity;

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
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Rol rol;

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "docente")
    @Builder.Default
    private Set<Curso> cursosDocente = new HashSet<>();

    @OneToMany(mappedBy = "estudiante")
    @Builder.Default
    private Set<Inscripcion> inscripciones = new HashSet<>();

    @OneToMany(mappedBy = "estudiante")
    @Builder.Default
    private Set<Entrega> entregas = new HashSet<>();

    @OneToMany(mappedBy = "estudiante")
    @Builder.Default
    private Set<Asistencia> asistencias = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Builder.Default
    private Set<Notificacion> notificaciones = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Builder.Default
    private Set<Auditoria> auditorias = new HashSet<>();
}
