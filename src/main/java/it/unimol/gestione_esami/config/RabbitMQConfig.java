package it.unimol.gestione_esami.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "exam.exchange";
    public static final String QUEUE_COURSE_UPDATED = "course.updated";
    public static final String QUEUE_COURSE_DELETED = "course.deleted";
    public static final String QUEUE_USER_UPDATED = "user.updated";
    public static final String QUEUE_CLASSROOM_UPDATED = "classroom.updated";


    @Bean
    public TopicExchange examExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public Queue courseUpdatedQueue() {
        return new Queue(QUEUE_COURSE_UPDATED);
    }

    @Bean
    public Queue courseDeletedQueue() {
        return new Queue(QUEUE_COURSE_DELETED);
    }

    @Bean
    public Queue userUpdatedQueue() {
        return new Queue(QUEUE_USER_UPDATED);
    }

    @Bean
    public Queue classroomUpdatedQueue() {
        return new Queue(QUEUE_CLASSROOM_UPDATED);
    }
}
