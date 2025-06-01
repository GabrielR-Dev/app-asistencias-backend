package com.asistencias.Asistencias.entities;

import jakarta.persistence.*;

@Entity
public class CodigoAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String codigo;
    private Long asistenciaId;

    public CodigoAsistencia() {
    }

    public CodigoAsistencia(String codigo, Long asistenciaId) {
        this.codigo = codigo;
        this.asistenciaId = asistenciaId;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getAsistenciaId() {
        return asistenciaId;
    }

    public void setAsistenciaId(Long asistenciaId) {
        this.asistenciaId = asistenciaId;
    }
}