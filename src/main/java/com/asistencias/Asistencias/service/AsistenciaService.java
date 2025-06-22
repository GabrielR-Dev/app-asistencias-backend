package com.asistencias.Asistencias.service;

import com.asistencias.Asistencias.dtos.AsistenciaDTO;
import com.asistencias.Asistencias.entities.Asistencia;
import com.asistencias.Asistencias.entities.CodigoAsistencia;
import com.asistencias.Asistencias.entities.Evento;
import com.asistencias.Asistencias.entities.Usuario;
import com.asistencias.Asistencias.repositories.IAsistenciaRepository;
import com.asistencias.Asistencias.repositories.ICodigoAsistenciaRepository;
import com.asistencias.Asistencias.repositories.IEventoRepository;
import com.asistencias.Asistencias.repositories.IUsuarioRepository;
import com.asistencias.Asistencias.utils.GeneradorCodigo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
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
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> obtenerTodas(Long idEvento) {
        /*String userLog = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByEmail(userLog).orElseThrow(
                () -> new AccessDeniedException("Usuario no reconocido.")
        );
        Evento evento = eventoRepository.findById(idEvento).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado")
        );
        if (!evento.getCreador().getId().equals(usuario.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "No puedes ver las asistencias de los eventos de otros usuarios"));
        }*/
        List<Asistencia> asistencias = asistenciaRepository.findByEventoId(idEvento);
        List<AsistenciaDTO> asistenciaDTOS = asistencias.stream()
                .map(a -> modelMapper.map(a, AsistenciaDTO.class))
                .toList();

//        return ResponseEntity.ok(Map.of("asistencias", asistenciaDTOS));
        return ResponseEntity.ok(asistenciaDTOS);
    }

    public ResponseEntity<?> obtenerPorId(Long idEvento, Long idAsistencia) {

        String userLog = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(userLog).orElseThrow(
                () -> new AccessDeniedException("Usuario no reconocido.")
        );

        Evento evento = eventoRepository.getReferenceById(idEvento);

        Asistencia asistencia = asistenciaRepository.getReferenceById(idAsistencia);


        if(evento.getCreador() != usuario) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No puedes ver las asistencias de los eventos de otros usuarios");
        if(evento.getId() != asistencia.getEventoId()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No puedes ver esta asistencia");

        AsistenciaDTO asistenciaDTO = modelMapper.map(asistencia,AsistenciaDTO.class);

        return ResponseEntity.ok().body(asistenciaDTO);
    }

    /*public ResponseEntity<?> crearAsistencia(Asistencia asistencia, Evento evento) {

        asistenciaRepository.save(asistencia);
        Asistencia asistenciaConId = asistenciaRepository.findByDescripcion(asistencia.getDescripcion());

        // Genera 10 códigos únicos de 6 caracteres y los agrega a la base de datos de CodigoAsistencia
        for (int i = 0; i < 10; i++) {
            String codigo = GeneradorCodigo.generarCodigo();
            CodigoAsistencia codigoAsistencia = new CodigoAsistencia(codigo,asistenciaConId.getId());
            codigoAsistenciaRepository.save(codigoAsistencia);
        }
        return ResponseEntity.ok(Map.of("mensaje", "Asistencia creada correctamente"));
    }*/
    public ResponseEntity<?> crearAsistencia(Asistencia asistencia, Evento evento) {
        asistencia.setEventoId(evento.getId());
        asistencia = asistenciaRepository.save(asistencia);

        // Generar y guardar 10 códigos únicos para esta asistencia
        for (int i = 0; i < asistencia.getCantColaboradores(); i++) {
            String codigo = GeneradorCodigo.generarCodigo();

            CodigoAsistencia codigoAsistencia = new CodigoAsistencia();
            codigoAsistencia.setCodigo(codigo);
            codigoAsistencia.setAsistenciaId(asistencia.getId());

            codigoAsistenciaRepository.save(codigoAsistencia);
        }

        return ResponseEntity.ok(Map.of("mensaje", "Asistencia creada correctamente"));
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
