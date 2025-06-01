package com.asistencias.Asistencias.service;

import com.asistencias.Asistencias.dtos.EventoResponseDTO;
import com.asistencias.Asistencias.entities.Evento;
import com.asistencias.Asistencias.entities.Usuario;
import com.asistencias.Asistencias.repositories.IAsistenciaRepository;
import com.asistencias.Asistencias.repositories.IEventoRepository;
import com.asistencias.Asistencias.repositories.ISuscripcionRepository;
import com.asistencias.Asistencias.repositories.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {
    @Autowired
    private IAsistenciaRepository asistenciaRepository;
    @Autowired
    private ISuscripcionRepository suscripcionRepository;
    @Autowired
    private IEventoRepository eventoRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<List<EventoResponseDTO>> obtenerTodosLosEventosDelUsuario(Long id) {
        String nameUsu = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usu = usuarioRepository.findByEmail(nameUsu)
                .orElseThrow(() -> new RuntimeException("El usuario no existe."));
        Usuario usuLog = usuarioRepository.getReferenceById(id);

        if (!usu.getId().equals(usuLog.getId())) throw new AccessDeniedException("No puedes ver los eventos de este usuario.");

        List<Evento> eventosDelUsuario = eventoRepository.findByCreadorId(id);

        // Convertir lista de Evento a lista de EventoDTO
        List<EventoResponseDTO> eventosDTO = eventosDelUsuario.stream()
                .map(evento -> modelMapper.map(evento, EventoResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventosDTO);
    }

    public ResponseEntity<?> crearEvento(Evento evento) {

        String u = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(u).orElseThrow();
        evento.setCreador(usuario);
        evento.setFechaCreacion(LocalDateTime.now());

        eventoRepository.save(evento);
        return ResponseEntity.ok("Evento creado con exito.");
    }


    //Este metodo borra tambien las suscripciones de usuarios a ese evento
    @Transactional
    public ResponseEntity<?> eliminarEvento(Long id) {

        String userLog = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(userLog)
                .orElseThrow(() -> new AccessDeniedException("Usuario no reconocido."));

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new AccessDeniedException("No existe este evento."));

        if (!evento.getCreador().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("No puedes borrar este evento.");
        }

        asistenciaRepository.deleteByEventoId(evento.getId());
        suscripcionRepository.deleteByEventoId(evento.getId());
        eventoRepository.deleteById(evento.getId());

        return ResponseEntity.status(HttpStatus.OK).body("Evento eliminado correctamente");
    }


}
