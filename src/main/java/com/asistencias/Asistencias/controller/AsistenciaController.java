package com.asistencias.Asistencias.controller;

import com.asistencias.Asistencias.dtos.AsistenciaDTO;
import com.asistencias.Asistencias.entities.Asistencia;
import com.asistencias.Asistencias.entities.CodigoAsistencia;
import com.asistencias.Asistencias.entities.Evento;
import com.asistencias.Asistencias.repositories.IAsistenciaRepository;
import com.asistencias.Asistencias.repositories.IEventoRepository;
import com.asistencias.Asistencias.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;
    @Autowired
    private IEventoRepository eventoRepository;

    @GetMapping
    public List<Asistencia> obtenerTodas() {
        return asistenciaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Asistencia obtenerPorId(@PathVariable Long id) {
        return asistenciaService.obtenerPorId(id).orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody AsistenciaDTO asistenciaDTO) {
        Evento evento = eventoRepository.findById(asistenciaDTO.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));


        Asistencia asistencia = new Asistencia(
                asistenciaDTO.getTitulo(),
                asistenciaDTO.getDescripcion(),
                asistenciaDTO.getNombreLugar(),
                asistenciaDTO.getDireccion(),
                asistenciaDTO.getFecha(),
                asistenciaDTO.getHoraInicio(),
                asistenciaDTO.getHoraFin(),
                asistenciaDTO.getEventoId()
        );

        return asistenciaService.crearAsistencia(asistencia, evento);
    }


    @DeleteMapping("/evento/{idEvento}/asistencia/{idAsistencia}")
    public ResponseEntity<?> eliminar(@PathVariable Long idEvento, @PathVariable Long idAsistencia) {
        return asistenciaService.eliminarAsistencia(idEvento, idAsistencia);
    }

}
