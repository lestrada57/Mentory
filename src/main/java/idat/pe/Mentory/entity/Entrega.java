package idat.pe.Mentory.entity;

import java.math.BigDecimal;
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
@Table(name = "entregas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tarea_id", nullable = false)
    private Tarea tarea;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Usuario estudiante;

    @Column(name = "archivo_key", columnDefinition = "TEXT")
    private String archivoKey;

    @Column(name = "nombre_archivo", length = 255)
    private String nombreArchivo;

    @Column(name = "peso_bytes")
    private Long pesoBytes;

    @Column(name = "formato", length = 50)
    private String formato;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "nota", precision = 5, scale = 2)
    private BigDecimal nota;

    @Column(name = "retroalimentacion", columnDefinition = "TEXT")
    private String retroalimentacion;
}
