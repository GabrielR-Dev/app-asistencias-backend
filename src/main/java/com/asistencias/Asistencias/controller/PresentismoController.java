package com.asistencias.Asistencias.controller;

import com.asistencias.Asistencias.dtos.AsistenciaDTO;
import com.asistencias.Asistencias.dtos.PresentismoDTO;
import com.asistencias.Asistencias.entities.Asistencia;
import com.asistencias.Asistencias.entities.Evento;
import com.asistencias.Asistencias.entities.Presentismo;
import com.asistencias.Asistencias.repositories.IEventoRepository;
import com.asistencias.Asistencias.service.PresentismoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/presentismos")
@CrossOrigin(origins = "*")
public class PresentismoController {

    @Autowired
    private PresentismoService presentismoService;
    @Autowired
    private IEventoRepository eventoRepository;


    @PostMapping("/{idAsistencia}")
    public ResponseEntity<?> registrarCodigo(@RequestBody PresentismoDTO presentismoDTO, @PathVariable Long idAsistencia) {
        return presentismoService.registrarPresente(presentismoDTO,idAsistencia);
    }

    @GetMapping("/random/evento/{eventoId}/asistencia/{asistenciaId}")
    public ResponseEntity<?> traerUnCodigoRandom(@PathVariable Long asistenciaId, @PathVariable Long eventoId) {

        return presentismoService.codigoRandom(asistenciaId, eventoId);
    }
    @GetMapping
    public ResponseEntity<?> estuvoPresente() {

        return presentismoService.estuvoPresente();
    }
    @GetMapping("/{idAsistencia}")
    public ResponseEntity<?> verLosPresentesDeUnaAsistencia(@PathVariable Long idAsistencia){
        return presentismoService.verPresentesDeMiAsistencia(idAsistencia);
    }

    @DeleteMapping("/{idAsistencia}")
    public ResponseEntity<?> borrarCodigo(@PathVariable Long idAsistencia) {
        try {
            presentismoService.borrarCodigos(idAsistencia);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace(); //imprimí la excepción en consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudieron borrar los códigos", "detalle", e.getMessage()));
        }
    }


    @GetMapping("/asistencia/{idAsistencia}")
    public ResponseEntity<?> verificarCodigoDesdeHeader(
            @PathVariable Long idAsistencia,
            @RequestHeader("codigoAsistencia") String codigoAsistencia) {
        return presentismoService.verCodigo(codigoAsistencia, idAsistencia);

    }
}
