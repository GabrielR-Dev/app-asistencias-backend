package com.asistencias.Asistencias.dtos;

import com.asistencias.Asistencias.entities.Usuario;

public class EventoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    //private String organizadorNombre;
    //private String organizadorApellido;
    //private String fechaCreacion;
    //private String codigoInvitacion;
    //private String creadorNombre;
    //private String creadorApellido;

    public EventoDTO() {
    }

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

}
