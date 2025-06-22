package com.asistencias.Asistencias.dtos;

public class EventoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String organizadorNombre;
    private String organizadorApellido;
    private String fechaCreacion;
    private String codigoInvitacion;
    private String creador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrganizadorNombre() {
        return organizadorNombre;
    }

    public void setOrganizadorNombre(String organizadorNombre) {
        this.organizadorNombre = organizadorNombre;
    }

    public String getOrganizadorApellido() {
        return organizadorApellido;
    }

    public void setOrganizadorApellido(String organizadorApellido) {
        this.organizadorApellido = organizadorApellido;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCodigoInvitacion() {
        return codigoInvitacion;
    }

    public void setCodigoInvitacion(String codigoInvitacion) {
        this.codigoInvitacion = codigoInvitacion;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }
    /*
    public String getCreadorNombre() {
        return creadorNombre;
    }

    public void setCreadorNombre(String creadorNombre) {
        this.creadorNombre = creadorNombre;
    }

    public String getCreadorApellido() {
        return creadorApellido;
    }

    public void setCreadorApellido(String creadorApellido) {
        this.creadorApellido = creadorApellido;
    }*/
}