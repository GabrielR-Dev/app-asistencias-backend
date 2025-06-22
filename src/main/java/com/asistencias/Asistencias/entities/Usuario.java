package com.asistencias.Asistencias.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class Usuario {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;


    @Column(unique = true, nullable = false)
    private String email;

    private String contrasenia;

    @CreationTimestamp
    private LocalDateTime fechaCreado;

    @UpdateTimestamp
    private LocalDateTime fechaActualizado;

    @ManyToMany
    @JoinTable(
            name = "usuario_evento",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    private List<Evento> eventosSuscripto = new ArrayList<>();

    //Constructores
    public Usuario() {
    }

    public Usuario(String nombre, String email, String contrasenia) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public Usuario(Long id, String nombre, String email, String contrasenia, LocalDateTime fechaActualizado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.fechaActualizado = fechaActualizado;
    }

    public Usuario(Long id, String nombre, String email, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public Usuario(Long id, String nombre, String email, String contrasenia, LocalDateTime fechaCreado, LocalDateTime fechaActualizado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.fechaCreado = fechaCreado;
        this.fechaActualizado = fechaActualizado;
    }

    public Usuario(String nombre, String apellido, String email, String contrasenia, LocalDateTime fechaCreado, LocalDateTime fechaActualizado, List<Evento> eventosSuscripto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasenia = contrasenia;
        this.fechaCreado = fechaCreado;
        this.fechaActualizado = fechaActualizado;
        this.eventosSuscripto = eventosSuscripto;
    }

    // Getters and Setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public LocalDateTime getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(LocalDateTime fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public LocalDateTime getFechaActualizado() {
        return fechaActualizado;
    }

    public void setFechaActualizado(LocalDateTime fechaActualizado) {
        this.fechaActualizado = fechaActualizado;
    }

    public List<Evento> getEventosSuscripto() {
        return eventosSuscripto;
    }

    public void addEventoSuscripto(Evento eventosSuscripto) {
        this.eventosSuscripto.add(eventosSuscripto);
    }
    public void removeEventoSuscripto(Evento eventosSuscripto) {
        this.eventosSuscripto.remove(eventosSuscripto);
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEventosSuscripto(List<Evento> eventosSuscripto) {
        this.eventosSuscripto = eventosSuscripto;
    }
}
