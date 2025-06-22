package com.asistencias.Asistencias.dtos;

public class PresentismoResponseDTO {

    Long id;
    Long asistenciaId;

    Long usuarioId;
    Integer presente;

    public PresentismoResponseDTO() {
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getAsistenciaId() {
        return asistenciaId;
    }

    public void setAsistenciaId(Long asistenciaId) {
        this.asistenciaId = asistenciaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getPresente() {
        return presente;
    }

    public void setPresente(Integer presente) {
        this.presente = presente;
    }
}
