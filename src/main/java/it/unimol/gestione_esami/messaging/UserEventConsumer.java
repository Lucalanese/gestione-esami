package it.unimol.gestione_esami.messaging;

import it.unimol.gestione_esami.entity.UserCache;
import it.unimol.gestione_esami.repository.ExamEnrollmentRepository;
import it.unimol.gestione_esami.repository.ExamRepository;
import it.unimol.gestione_esami.repository.UserCacheRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserEventConsumer {

    private final ExamRepository examRepository;
    private final ExamEnrollmentRepository examEnrollmentRepository;
    private final UserCacheRepository userCacheRepository;

    public UserEventConsumer(ExamRepository examRepository, ExamEnrollmentRepository examEnrollmentRepository,
                             UserCacheRepository userCacheRepository) {
        this.examRepository = examRepository;
        this.examEnrollmentRepository = examEnrollmentRepository;
        this.userCacheRepository = userCacheRepository;
    }

    @RabbitListener(queues = "user.updated")
    public void handleUserUpdated(Map<String, Object> message) {
        String userId = (String) message.get("userId");
        String name = (String) message.get("name");
        String surname = (String) message.get("surname");
        String roleId = (String) message.get("roleId");
        String fullName = name + " " + surname;

        UserCache cache = new UserCache(Long.parseLong(userId), fullName, roleId);
        userCacheRepository.save(cache);

        if ("teach".equals(roleId) || "admin".equals(roleId)) {
            Long professorId = Long.parseLong(userId);
            examRepository.findByProfessorId(professorId).forEach(exam -> {
                exam.setProfessorName(fullName);
                examRepository.save(exam);
            });

        }
        if ("student".equals(roleId)) {
            Long studentId = Long.parseLong(userId);
            examEnrollmentRepository.findByStudentId(studentId).forEach(enrollment -> {
                enrollment.setStudentName(fullName);
                examEnrollmentRepository.save(enrollment);
            });
        }

    }
}
