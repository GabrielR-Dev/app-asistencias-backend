package com.asistencias.Asistencias.controller;

import com.asistencias.Asistencias.dtos.EventoResponseDTO;
import com.asistencias.Asistencias.entities.Evento;
import com.asistencias.Asistencias.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<EventoResponseDTO>> obtenerTodosDelUsuario(@PathVariable Long id) {
        return eventoService.obtenerTodosLosEventosDelUsuario(id);
    }


    /*@GetMapping("/{idEvento}")
    public Evento obtenerPorId(@PathVariable Long idEvento) {
        return eventoService.obtenerPorId(idEvento).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
    }*/

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Evento evento) {
        return eventoService.crearEvento(evento);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return eventoService.eliminarEvento(id);
    }

}
