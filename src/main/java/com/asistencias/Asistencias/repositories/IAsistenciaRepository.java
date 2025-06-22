package com.asistencias.Asistencias.repositories;

import com.asistencias.Asistencias.entities.Asistencia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAsistenciaRepository extends JpaRepository<Asistencia,Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Asistencia a WHERE a.eventoId = :eventoId")
    void deleteByEventoId(@Param("eventoId") Long eventoId);

    List<Asistencia> findByEventoId(Long idEvento);

    Asistencia findByDescripcion(String descripcion);
}
