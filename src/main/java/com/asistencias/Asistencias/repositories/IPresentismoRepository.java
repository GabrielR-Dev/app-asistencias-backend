package com.asistencias.Asistencias.repositories;

import com.asistencias.Asistencias.entities.Presentismo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPresentismoRepository extends JpaRepository<Presentismo,Long> {
}
