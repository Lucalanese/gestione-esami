package it.unimol.gestione_esami.messaging;

import it.unimol.gestione_esami.config.RabbitMQConfig;
import it.unimol.gestione_esami.dto.EnrollmentDTO;
import it.unimol.gestione_esami.dto.ExamDTO;
import it.unimol.gestione_esami.dto.GradeDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExamEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ExamEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishExamCreated(ExamDTO exam) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "exam.created", exam);
    }

    public void publishExamUpdated(ExamDTO exam) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "exam.updated", exam);
    }

    public void publishExamDeleted(Long examId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "exam.deleted", examId);
    }

    public void publishEnrollmentRequested(EnrollmentDTO enrollment){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "exam.enrollment.requested", enrollment);
    }

    public void publishGradeRecorded(GradeDTO grade){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "exam.grade.recorded", grade);
    }
}
