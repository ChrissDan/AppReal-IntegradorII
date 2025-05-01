package com.integradorII.backend.Config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"));
        System.out.println("✅ Zona horaria por defecto: " + TimeZone.getDefault().getID());
    }
}

