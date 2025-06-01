package com.asistencias.Asistencias.service;

import com.asistencias.Asistencias.entities.Usuario;
import com.asistencias.Asistencias.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository userRepository; // o el DAO que estés usando

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        UserDetails us = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getContrasenia(),
                Collections.emptyList() // o tus roles/authorities
        );
        System.out.println(user.getEmail()+ " -------------- " + user.getContrasenia());
        System.out.println("Este es es userDetails: ---------------------> "+ us);
        return us;
    }
}