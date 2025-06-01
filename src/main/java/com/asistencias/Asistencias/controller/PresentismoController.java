package com.asistencias.Asistencias.controller;

import com.asistencias.Asistencias.entities.Asistencia;
import com.asistencias.Asistencias.entities.Presentismo;
import com.asistencias.Asistencias.service.PresentismoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presentismos")
@CrossOrigin(origins = "*")
public class PresentismoController {

    @Autowired
    private PresentismoService presentismoService;


    @PostMapping("/{idAsistencia}")
    public ResponseEntity<?> registrar(@RequestBody Presentismo presentismo, @PathVariable Long idAsistencia) {
        return presentismoService.registrarPresente(presentismo,idAsistencia);
    }

    @GetMapping("/random/{idAsistencia}")
    public String traerCodigoRandom(@PathVariable Long idAsistencia) {
        String codigo = String.valueOf(presentismoService.codigoRandom(idAsistencia));
        if (codigo == null) {
            return "No se encontró código para esa asistencia.";
        }
        return codigo;
    }


}
