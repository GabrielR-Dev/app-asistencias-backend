package com.asistencias.Asistencias.controller;

import com.asistencias.Asistencias.dtos.EventoDTO;
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

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> buscarPorCodigoInvitacion(@PathVariable String codigo) {
        return eventoService.buscarPorCodInvitado(codigo);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody EventoDTO evento) {
        return eventoService.crearEvento(evento);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return eventoService.eliminarEvento(id);
    }

}
