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

    @Value("${queue.beta.request}")
    private String paymentRequest;

    @Value("${queue.beta.response}")
    private String paymentResponse;

    @Value("${queue.intra.payment.slip.name}")
    private String slipQueueName;

    @Value("${queue.intra.payment.slip.routing-key}")
    private String slipQueueRoutingKey;

    @Value("${queue.intra.payment.credit-card.name}")
    private String creditCardQueueName;

    @Value("${queue.intra.payment.credit-card.routing-key}")
    private String creditCardQueueRoutingKey;

    @Value("${queue.intra.payment.result.name}")
    private String resultQueueName;

    @Value("${queue.intra.payment.result.routing-key}")
    private String resultQueueRoutingKey;

    @Value("${queue.intra.exchange}")
    public String exchangeName;


    // request - response
    @Bean
    public Queue paymentRequestQueue() {
        return new Queue(paymentRequest, true);
    }

    @Bean
    public Queue paymentResponseQueue() {
        return new Queue(paymentResponse, true);
    }

    // intra application
    @Bean
    public Queue slipQueue() {
        return new Queue(slipQueueName, true);
    }

    @Bean
    public Queue creditCardQueue() {
        return new Queue(creditCardQueueName, true);
    }

    @Bean
    public Queue resultQueue() {
        return new Queue(resultQueueName, true);
    }

    @Bean
    public Queue parkQueue() {
        return new Queue("park", true);
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

    @Bean
    public Binding resultBinding(Queue resultQueue, DirectExchange exchange) {
        return BindingBuilder.bind(resultQueue).to(exchange).with(resultQueueRoutingKey);
    }

    @Bean
    public Binding parkBinding(Queue parkQueue, DirectExchange exchange) {
        return BindingBuilder.bind(parkQueue).to(exchange).with("beta.payment.park");
    }
}