package br.com.devrodrigues.orchestrator.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${queue.intra.payment.slip.name}")
    private String slipQueueName;

    @Value("${queue.intra.payment.slip.routing-key}")
    private String slipQueueRoutingKey;

    @Value("${queue.intra.payment.credit-card.name}")
    private String creditCardQueueName;

    @Value("${queue.intra.payment.credit-card.routing-key}")
    private String creditCardQueueRoutingKey;

    @Value("${queue.intra.exchange}")
    public String exchangeName;

    @Bean
    public Queue slipQueue() {
        return new Queue(slipQueueName, true);
    }

    @Bean
    public Queue creditCardQueue() {
        return new Queue(creditCardQueueName, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding slipBinding(Queue slipQueue, DirectExchange exchange) {
        return BindingBuilder.bind(slipQueue).to(exchange).with(slipQueueRoutingKey);
    }

    @Bean
    public Binding creditCardBinding(Queue creditCardQueue, DirectExchange exchange) {
        return BindingBuilder.bind(creditCardQueue).to(exchange).with(creditCardQueueRoutingKey);
    }
}