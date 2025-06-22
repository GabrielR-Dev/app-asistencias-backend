package com.asistencias.Asistencias.controller;


import com.asistencias.Asistencias.config.JwtUtil;
import com.asistencias.Asistencias.dtos.AuthRequest;
import com.asistencias.Asistencias.dtos.AuthResponse;
import com.asistencias.Asistencias.dtos.UsuarioDTO;
import com.asistencias.Asistencias.entities.Usuario;
import com.asistencias.Asistencias.repositories.IUsuarioRepository;
import com.asistencias.Asistencias.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/users/auth")
@Tag(name = "Users", description = "Operaciones relacionadas al inicio de sesion")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(summary = "Endpoint iniciar sesion",description = "Este endpoin iniciar sesion. " +
            "No necesita token")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasenia()));

        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        String jwt = jwtUtil.generateToken(user);

        String usuarioLogueado = user.getUsername();
        Usuario usuario = usuarioRepository.findByEmail(usuarioLogueado).orElseThrow();
        Long id = usuario.getId();
        String email = usuario.getEmail();
        String nombre = usuario.getNombre();
        String apellido = usuario.getApellido();
        return ResponseEntity.ok(Map.of(
                "mensaje", "Login exitoso",
                "token", jwt,
                "usuarioLogueado",email,
                "idUsuario",id,
                "nombre",nombre,
                "apellido", apellido
        ));
    }

    @PostMapping("/register")
    @Operation(summary = "Endpoint para registrarse ")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.createUser(usuarioDTO);
    }
}
