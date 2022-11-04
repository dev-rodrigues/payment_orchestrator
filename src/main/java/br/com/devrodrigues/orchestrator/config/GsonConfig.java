package br.com.devrodrigues.orchestrator.config;

import br.com.devrodrigues.orchestrator.core.adapter.GsonLocalDateTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class GsonConfig {

    private final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(
            LocalDateTime.class, new GsonLocalDateTime()
    );

    @Bean
    public Gson gson () {
        return gsonBuilder.setPrettyPrinting().create();
    }
}
