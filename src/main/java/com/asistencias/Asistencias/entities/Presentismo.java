package com.asistencias.Asistencias.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "presentismo")
public class Presentismo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean presente;
    private LocalDateTime fechaMarcado;

    private Long usuarioId;

    private Long asistenciaId;

    private String codigo;

    public Presentismo() {
    }

    public Presentismo(boolean presente, LocalDateTime fechaMarcado, Long usuarioId, Long asistenciaId, String codigoAsistencia) {
        this.presente = presente;
        this.fechaMarcado = fechaMarcado;
        this.usuarioId = usuarioId;
        this.asistenciaId = asistenciaId;
        this.codigo = codigoAsistencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public LocalDateTime getFechaMarcado() {
        return fechaMarcado;
    }

    public void setFechaMarcado(LocalDateTime fechaMarcado) {
        this.fechaMarcado = fechaMarcado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getAsistenciaId() {
        return asistenciaId;
    }

    public void setAsistenciaId(Long asistenciaId) {
        this.asistenciaId = asistenciaId;
    }

    public String getCodigoAsistencia() {
        return codigo;
    }

    public void setCodigoAsistencia(String codigoAsistencia) {
        this.codigo = codigoAsistencia;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
