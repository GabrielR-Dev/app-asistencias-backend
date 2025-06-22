package com.asistencias.Asistencias.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String nombreLugar;
    private String direccion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fecha;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaFin;
    private Long eventoId;

    private int cantColaboradores;


    public Asistencia() {
    }



    public Asistencia(String descripcion, String nombreLugar, String direccion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Long eventoId) {
        this.descripcion = descripcion;
        this.nombreLugar = nombreLugar;
        this.direccion = direccion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.eventoId = eventoId;
    }

    public Asistencia(String descripcion, String nombreLugar, String direccion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.descripcion = descripcion;
        this.nombreLugar = nombreLugar;
        this.direccion = direccion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Asistencia(String titulo, String descripcion, String nombreLugar, String direccion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Long eventoId, int cantColaboradores) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.nombreLugar = nombreLugar;
        this.direccion = direccion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.eventoId = eventoId;
        this.cantColaboradores = cantColaboradores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCantColaboradores() {
        return cantColaboradores;
    }

    public void setCantColaboradores(int cantColaboradores) {
        this.cantColaboradores = cantColaboradores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha= fecha;
    }


    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }


}
