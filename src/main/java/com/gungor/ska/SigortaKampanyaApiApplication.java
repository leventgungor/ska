package com.gungor.ska;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "Sigorta Kampanya API",
        version = "0.0.1",
        description = "Sigorta Kampanyaları için CRUD Servisi"
))
@SpringBootApplication
public class SigortaKampanyaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigortaKampanyaApiApplication.class, args);
    }

}
