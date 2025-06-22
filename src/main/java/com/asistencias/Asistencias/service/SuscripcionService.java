package com.asistencias.Asistencias.service;

import com.asistencias.Asistencias.dtos.EventoResponseDTO;
import com.asistencias.Asistencias.dtos.SuscripcionDTO;
import com.asistencias.Asistencias.entities.Asistencia;
import com.asistencias.Asistencias.entities.Evento;
import com.asistencias.Asistencias.entities.Suscripcion;
import com.asistencias.Asistencias.entities.Usuario;
import com.asistencias.Asistencias.repositories.IAsistenciaRepository;
import com.asistencias.Asistencias.repositories.IEventoRepository;
import com.asistencias.Asistencias.repositories.ISuscripcionRepository;
import com.asistencias.Asistencias.repositories.IUsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.rmi.AccessException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SuscripcionService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IEventoRepository eventoRepository;
    @Autowired
    private ISuscripcionRepository suscripcionRepository;
    @Autowired
    private ModelMapper modelMapper;
    public ResponseEntity<?> suscripcion(SuscripcionDTO suscripcionDTO) {

        String userLog = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(userLog).orElseThrow(()->{return new AccessDeniedException("No puedes suscribite con este usuario.");
        });

        Evento evento = eventoRepository.findByCodigoInvitacion(suscripcionDTO.getCodigoInvitacion()).orElseThrow(()->{return new AccessDeniedException("El codigo de invitacion no esta registrado.");
        });

        if(evento.getCreador() == usuario){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No puedes suscribirte a tu evento.");
        }

        Suscripcion suscripcion = new Suscripcion(usuario,evento);

        suscripcionRepository.save(suscripcion);
        return ResponseEntity.ok(Map.of("message", "Usuario suscripto correctamente."));
    }

    public ResponseEntity<?> suscripcionesDelUsuario() {
        String userLog = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByEmail(userLog)
                .orElseThrow(() -> new AccessDeniedException("No puedes ver las suscripciones con este usuario."));

        // Trae todas las suscripciones del usuario
        List<Suscripcion> misSuscripciones = suscripcionRepository.findAllByUsuarioId(usuario.getId());

        // Mapear cada suscripción a su evento
        List<Evento> eventosSuscripto = misSuscripciones.stream()
                .map(Suscripcion::getEvento)
                .collect(Collectors.toList());
        List<EventoResponseDTO> eventoResponseDTOS = eventosSuscripto.stream()
                .map((evento)-> modelMapper.map(evento, EventoResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventoResponseDTOS);
    }


    public ResponseEntity<?> eliminarSuscripcion(Long eventoId) {
        String emailUsuario = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        // Buscar la suscripción del usuario al evento
        Suscripcion suscripcion = suscripcionRepository.findAllByUsuarioId(usuario.getId()).stream()
                .filter(s -> s.getEvento().getId().equals(eventoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No estás suscripto a este evento."));

        // Eliminar
        suscripcionRepository.delete(suscripcion);

        return ResponseEntity.ok(Map.of("message", "Te desuscribiste correctamente."));

    }
}
