package com.asistencias.Asistencias.controller;


import com.asistencias.Asistencias.dtos.SuscripcionDTO;
import com.asistencias.Asistencias.repositories.IUsuarioRepository;
import com.asistencias.Asistencias.service.SuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suscripcion")
public class SuscribcionController {

    @Autowired
    private SuscripcionService suscripcionService;
    @PostMapping
    public ResponseEntity<?> suscribirse (@RequestBody SuscripcionDTO suscripcionDTO){
        return suscripcionService.suscripcion(suscripcionDTO);
    }

    @GetMapping
    public ResponseEntity<?> traerSuscripcionesUsuario (){
        return  suscripcionService.suscripcionesDelUsuario();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desuscribirse (@PathVariable Long id){

        return suscripcionService.eliminarSuscripcion(id);
    }
}
