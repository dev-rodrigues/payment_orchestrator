package br.com.devrodrigues.orchestrator;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class PaymentOrchestratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentOrchestratorApplication.class, args);
    }

}
