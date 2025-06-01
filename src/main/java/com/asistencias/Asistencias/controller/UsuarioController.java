package com.asistencias.Asistencias.controller;

import com.asistencias.Asistencias.dtos.UsuarioDTO;
import com.asistencias.Asistencias.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Peticiones para Usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;
    @Operation(summary = "Listar todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {

        return userService.getAllUsers();
    }

    @Operation(summary = "Trae el usuario con el ID que se le pasa en la URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);

    }
    @Operation(summary = "Trae el usuario con el Email que se le pasa en la URL")
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    //Este endpoint no va ser necesario ya que el regitro crea el usuario
    /*@Operation(summary = "Endpoint para crear un Usuario")
    @PostMapping("/auth")
    public ResponseEntity<?> createUser(@RequestBody UsuarioDTO user) {
        return userService.createUser(user);
    }*/

    @Operation(summary = "Elimina el Usuario con el ID que se le pasa en la URL")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "Edita el Usuario con el ID que se le pasa en la URL")
    @Parameter(name = "id", description = "ID del usuario a editar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UsuarioDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }
}
