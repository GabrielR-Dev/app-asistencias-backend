package com.asistencias.Asistencias.service;

import com.asistencias.Asistencias.entities.CodigoAsistencia;
import com.asistencias.Asistencias.entities.Presentismo;
import com.asistencias.Asistencias.entities.Usuario;
import com.asistencias.Asistencias.repositories.IAsistenciaRepository;
import com.asistencias.Asistencias.repositories.ICodigoAsistenciaRepository;
import com.asistencias.Asistencias.repositories.IPresentismoRepository;
import com.asistencias.Asistencias.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PresentismoService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IAsistenciaRepository asistenciaRepository;
    @Autowired
    private IPresentismoRepository presentismoRepository;
    @Autowired
    private ICodigoAsistenciaRepository codigoAsistenciaRepository;

    /*public List<Presentismo> obtenerTodos() {
        return presentismoRepository.findAll();
    }

    public Optional<Presentismo> obtenerPorId(Long id) {
        return presentismoRepository.findById(id);
    }*/

    public ResponseEntity<?> registrarPresente(Presentismo presentismo, Long idAsistencia) {
        List<CodigoAsistencia> codigos = codigoAsistenciaRepository.obtenerPorAsistenciaId(idAsistencia);

        // Verifica si el código ingresado existe
        Optional<CodigoAsistencia> codigoValido = codigos.stream()
                .filter(c -> c.getCodigo().equals(presentismo.getCodigoAsistencia()))
                .findFirst();

        if (codigoValido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El código no es válido.");
        }

        // Obtiene el usuario logueado
        String uName = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLog = usuarioRepository.findByEmail(uName).orElseThrow();

        // Completa el presentismo
        presentismo.setFechaMarcado(LocalDateTime.now());
        presentismo.setPresente(true);
        presentismo.setUsuarioId(usuarioLog.getId());
        presentismo.setAsistenciaId(idAsistencia);

        // Guarda el presentismo
        presentismoRepository.save(presentismo);


        // Elimina el código usado
        codigoAsistenciaRepository.deleteByCodigo(presentismo.getCodigoAsistencia());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registro de asistencia correcto.");
    }

    public String codigoRandom(Long idAsistencia) {
        List<CodigoAsistencia> codigos = codigoAsistenciaRepository.obtenerPorAsistenciaId(idAsistencia);

        /*if (codigos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El codigo es incorrecto o no existe.");
        }*/

        int randomIndex = (int) (Math.random() * codigos.size());
        return codigos.get(randomIndex).getCodigo();
    }
}
