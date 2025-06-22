package com.asistencias.Asistencias.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GeneradorCodigo {
    @Bean
    public static String generarCodigo() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, 6).toUpperCase();
    }
    @Bean
    public static String generarCodigoInvitacion() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, 10).toUpperCase();
    }
}
