package com.asistencias.Asistencias.service;

import com.asistencias.Asistencias.dtos.AuthResponse;
import com.asistencias.Asistencias.dtos.PresentismoDTO;
import com.asistencias.Asistencias.dtos.PresentismoResponseDTO;
import com.asistencias.Asistencias.dtos.UsuarioResponseDTO;
import com.asistencias.Asistencias.entities.*;
import com.asistencias.Asistencias.repositories.*;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private IEventoRepository eventoRepository;
    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<?> registrarPresente(PresentismoDTO presentismoDTO, Long idAsistencia) {
        List<CodigoAsistencia> codigos = codigoAsistenciaRepository.obtenerPorAsistenciaId(idAsistencia);

        Optional<CodigoAsistencia> codigoValido = codigos.stream()
                .filter(c -> c.getCodigo().equals(presentismoDTO.getCodigoAsistencia()))
                .findFirst();

        if (codigoValido.isEmpty()) {
            // Mejor devolver status 200 y decir que no es válido en body
            return ResponseEntity.ok(Map.of(
                    "success", false,
                    "message", "El código no es válido."
            ));
        }

        String uName = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLog = usuarioRepository.findByEmail(uName).orElseThrow();

        Presentismo presentismo = new Presentismo(true, LocalDateTime.now(), usuarioLog.getId(), idAsistencia, presentismoDTO.getCodigoAsistencia());
        presentismoRepository.save(presentismo);
        codigoAsistenciaRepository.delete(codigoValido.get());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Registro de asistencia correcto."
        ));
    }


    public ResponseEntity<?> codigoRandom(Long idAsistencia, Long idEvento) {
        String userLog = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(userLog).orElseThrow(
                () -> new AccessDeniedException("Usuario no reconocido.")
        );



        // Verificar que el evento exista
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new AccessDeniedException("No tienes acceso a este evento"));

        if(!evento.getCreador().equals(usuario)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No puedes ver las asistencias de este evento.");

        // Verificar que la asistencia pertenezca al evento
        Asistencia asistencia = asistenciaRepository.findById(idAsistencia)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La asistencia no existe"));

        if (!asistencia.getEventoId().equals(evento.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "La asistencia no pertenece al evento especificado"));
        }

        // Buscar los códigos de asistencia relacionados
        List<CodigoAsistencia> codigos = codigoAsistenciaRepository.findAllByAsistenciaId(idAsistencia);

        if (codigos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay códigos disponibles para esta asistencia."));
        }

        // Seleccionar un código aleatorio
        int randomIndex = (int) (Math.random() * codigos.size());
        String codigoSeleccionado = codigos.get(randomIndex).getCodigo();

        return ResponseEntity.ok(Map.of("codigo", codigoSeleccionado));
    }

    public void borrarCodigos(Long idAsistencia) {
        Asistencia asistencia = asistenciaRepository.findById(idAsistencia)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));

        List<CodigoAsistencia> codigos = codigoAsistenciaRepository.findAllByAsistenciaId(idAsistencia);

        if (codigos.isEmpty()) {
            throw new RuntimeException("No hay códigos para eliminar en esta asistencia");
        }

        codigoAsistenciaRepository.deleteAll(codigos);
    }

    public ResponseEntity<List<PresentismoResponseDTO>> estuvoPresente() {
        String uName = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLog = usuarioRepository.findByEmail(uName).orElseThrow();


        List<Presentismo> estuvoPresenteEn = presentismoRepository.findAllByUsuarioId(usuarioLog.getId());

        List<PresentismoResponseDTO> estuvoPresenteDTOs = estuvoPresenteEn.stream()
                .map(e -> modelMapper.map(e, PresentismoResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(estuvoPresenteDTOs); // Esto retorna un JSON
    }

    public ResponseEntity<?> verPresentesDeMiAsistencia(Long idAsistencia) {
        // Obtener usuario logueado
        String emailLogueado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLog = usuarioRepository.findByEmail(emailLogueado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Buscar la asistencia
        Asistencia asistencia = asistenciaRepository.findById(idAsistencia)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));

        // Verificar que el evento le pertenece al usuario
        boolean pertenece = eventoRepository.findByCreadorId(usuarioLog.getId()).stream()
                .anyMatch(evento -> evento.getId().equals(asistencia.getEventoId()));

        if (!pertenece) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes acceso a esta asistencia");
        }

        // Buscar los presentismos y mapear a DTO
        List<Presentismo> presentismos = presentismoRepository.findByAsistenciaId(idAsistencia);

        List<UsuarioResponseDTO> presentes = presentismos.stream()
                .map(p -> usuarioRepository.findById(p.getUsuarioId()))
                .filter(Optional::isPresent)
                .map(optUser -> {
                    Usuario u = optUser.get();
                    return new UsuarioResponseDTO(
                            u.getId(),
                            u.getNombre(),
                            u.getApellido(),
                            u.getEmail()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(presentes);
    }

    public ResponseEntity<?> verCodigo(String codigoDTO, Long idAsistencia) {

        List<CodigoAsistencia> codigos = codigoAsistenciaRepository.findAllByAsistenciaId(idAsistencia);
        boolean existe = codigos.stream()
                .anyMatch(codigo -> codigo.getCodigo().equals(codigoDTO));

        if (existe) {
            return ResponseEntity.noContent().build();
        }

        if (codigos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay códigos disponibles para esta asistencia."));
        }

        // Seleccionar un código aleatorio
        int randomIndex = (int) (Math.random() * codigos.size());
        String codigoSeleccionado = codigos.get(randomIndex).getCodigo();

        return ResponseEntity.ok(Map.of("codigo", codigoSeleccionado));

    }


}