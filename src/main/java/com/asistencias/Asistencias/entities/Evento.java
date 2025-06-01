package com.asistencias.Asistencias.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String codigoInvitacion;

    @ManyToOne
    @JoinColumn(name = "creador_id", nullable = false)
    private Usuario creador;

    private LocalDateTime fechaCreacion;

    private List<Long> asistencias = new ArrayList<>();

    public Evento() {
    }

    public Evento(String nombre, String descripcion, String codigoInvitacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigoInvitacion = codigoInvitacion;
    }

    public Evento(String nombre, String descripcion, String codigoInvitacion, Usuario creador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigoInvitacion = codigoInvitacion;
        this.creador = creador;
    }

    public Evento(Long id, String nombre, String descripcion, String codigoInvitacion, Usuario creador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigoInvitacion = codigoInvitacion;
        this.creador = creador;
    }

    public Evento(Long id, String nombre, String descripcion, String codigoInvitacion, Usuario creador, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigoInvitacion = codigoInvitacion;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
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

    public String getCodigoInvitacion() {
        return codigoInvitacion;
    }

    public void setCodigoInvitacion(String codigoInvitacion) {
        this.codigoInvitacion = codigoInvitacion;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Long> getAsistencias() {
        return this.asistencias ;
    }

    public void addAsistencia(Long asistencias) {
        this.asistencias.add(asistencias);
    }
    public void removeAsistencia(Long asistencias) {
        this.asistencias.remove(asistencias);
    }
}
