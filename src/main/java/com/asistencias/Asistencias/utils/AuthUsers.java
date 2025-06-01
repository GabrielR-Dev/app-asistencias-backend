package com.asistencias.Asistencias.utils;

import com.asistencias.Asistencias.entities.Usuario;
import com.asistencias.Asistencias.repositories.IUsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUsers {

    @Autowired
    IUsuarioRepository usuarioRepository;

    ModelMapper modelMapper;

    public boolean UsuarioConect(Long idEntity){
        Object usuarioConect = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = modelMapper.map(usuarioConect, Usuario.class);

        return false;
    }
}
