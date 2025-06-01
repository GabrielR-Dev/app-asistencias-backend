package com.asistencias.Asistencias.dtos;

public class EventoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoInvitacion;
    private CreadorDTO creador;


    public static class CreadorDTO {
        private Long id;
        private String nombre;
        private String email;

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

    public CreadorDTO getCreador() {
        return creador;
    }

    public void setCreador(CreadorDTO creador) {
        this.creador = creador;
    }
}