package idat.pe.Mentory.repository;

import idat.pe.Mentory.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT COUNT(v), SUM(v.total) FROM Venta v WHERE v.fecha BETWEEN :inicio AND :fin")
    Object[] getTotalVentas(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query("SELECT v.inscripcion.curso.id, v.inscripcion.curso.nombre, COUNT(v), SUM(v.total) " +
           "FROM Venta v WHERE v.fecha BETWEEN :inicio AND :fin " +
           "GROUP BY v.inscripcion.curso.id, v.inscripcion.curso.nombre")
    List<Object[]> getVentasAgrupadasPorCurso(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
