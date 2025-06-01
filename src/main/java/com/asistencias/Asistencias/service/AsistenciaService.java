package com.asistencias.Asistencias.service;

import com.asistencias.Asistencias.entities.Asistencia;
import com.asistencias.Asistencias.entities.CodigoAsistencia;
import com.asistencias.Asistencias.entities.Evento;
import com.asistencias.Asistencias.entities.Usuario;
import com.asistencias.Asistencias.repositories.IAsistenciaRepository;
import com.asistencias.Asistencias.repositories.ICodigoAsistenciaRepository;
import com.asistencias.Asistencias.repositories.IEventoRepository;
import com.asistencias.Asistencias.repositories.IUsuarioRepository;
import com.asistencias.Asistencias.utils.GeneradorCodigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService {
    @Autowired
    private IAsistenciaRepository asistenciaRepository;
    @Autowired
    private ICodigoAsistenciaRepository codigoAsistenciaRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IEventoRepository eventoRepository;

    public List<Asistencia> obtenerTodas() {
        return asistenciaRepository.findAll();
    }

    public Optional<Asistencia> obtenerPorId(Long id) {
        return asistenciaRepository.findById(id);
    }

    public ResponseEntity<?> crearAsistencia(Asistencia asistencia, Evento evento) {
        asistencia.setEventoId(evento.getId());

        asistenciaRepository.save(asistencia);

        // Genera 10 códigos únicos de 6 caracteres y los agrega a la base de datos de CodigoAsistencia
        for (int i = 0; i < 10; i++) {
            String codigo = GeneradorCodigo.generarCodigo();
            CodigoAsistencia codigoAsistencia = new CodigoAsistencia(codigo,asistencia.getId());
            codigoAsistenciaRepository.save(codigoAsistencia);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Asistencia creada correctamente");
    }

    public ResponseEntity<?> eliminarAsistencia(Long idEvento, Long idAsistencia) {
        String userLog = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(userLog).orElseThrow(
                () -> new AccessDeniedException("Usuario no reconocido.")
        );

        Evento evento = eventoRepository.findById(idEvento).orElseThrow(
                () -> new AccessDeniedException("El evento no existe o no tienes acceso.")
        );

        Asistencia asistencia = asistenciaRepository.findById(idAsistencia).orElseThrow(
                () -> new AccessDeniedException("No existe la asistencia con este ID.")
        );

        // Validar si el usuario es el creador del evento
        if (!evento.getCreador().equals(usuario)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No puedes eliminar esta asistencia");
        }

        // Validar si la asistencia pertenece al evento
        if (!evento.getId().equals(asistencia.getEventoId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No puedes eliminar esta asistencia");
        }

        asistenciaRepository.deleteById(idAsistencia);
        return ResponseEntity.status(HttpStatus.OK).body("Asistencia eliminada correctamente.");
    }
}
