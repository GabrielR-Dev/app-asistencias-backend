package com.asistencias.Asistencias.repositories;

import com.asistencias.Asistencias.entities.Asistencia;
import com.asistencias.Asistencias.entities.Evento;
import com.asistencias.Asistencias.entities.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEventoRepository extends JpaRepository<Evento,Long> {

    List<Evento> findByCreadorId(Long id);

    Optional<Evento> findByCodigoInvitacion(String codigoInvitacion);

    @Modifying
    @Transactional
    @Query("DELETE FROM Evento e WHERE e.id = :eventoId")
    void deleteById(@Param("eventoId") Long eventoId);
}
