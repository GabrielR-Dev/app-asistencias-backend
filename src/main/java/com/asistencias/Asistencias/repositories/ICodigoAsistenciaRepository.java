package com.asistencias.Asistencias.repositories;

import com.asistencias.Asistencias.entities.CodigoAsistencia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICodigoAsistenciaRepository extends JpaRepository<CodigoAsistencia,Long> {

    //JPQL
    @Modifying
    @Transactional
    @Query("DELETE FROM CodigoAsistencia c WHERE c.codigo = :codigo")
    void deleteByCodigo(@Param("codigo") String codigo);


    //JPA
    //List<CodigoAsistencia> findAllByAsistenciaId(Long idAsistencia);
    //JPQL
    @Query("SELECT c FROM CodigoAsistencia c WHERE c.asistenciaId = :idAsistencia")
    List<CodigoAsistencia> obtenerPorAsistenciaId(@Param("idAsistencia") Long idAsistencia);

}
