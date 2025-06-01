package com.asistencias.Asistencias.repositories;

import com.asistencias.Asistencias.entities.Suscripcion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISuscripcionRepository extends JpaRepository<Suscripcion,Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Suscripcion s WHERE s.evento.id = :eventoId")
    void deleteByEventoId(@Param("eventoId") Long eventoId);
    List<Suscripcion> findAllByUsuarioId(Long id);
}
