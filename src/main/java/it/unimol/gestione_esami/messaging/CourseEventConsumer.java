package it.unimol.gestione_esami.messaging;

import it.unimol.gestione_esami.entity.ClassroomCache;
import it.unimol.gestione_esami.entity.CourseCache;
import it.unimol.gestione_esami.repository.ClassroomCacheRepository;
import it.unimol.gestione_esami.repository.CourseCacheRepository;
import it.unimol.gestione_esami.repository.ExamRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CourseEventConsumer {

    private final ExamRepository examRepository;
    private final CourseCacheRepository courseCacheRepository;
    private final ClassroomCacheRepository classroomCacheRepository;

    public CourseEventConsumer(ExamRepository examRepository, CourseCacheRepository courseCacheRepository,
                               ClassroomCacheRepository classroomCacheRepository) {
        this.examRepository = examRepository;
        this.courseCacheRepository = courseCacheRepository;
        this.classroomCacheRepository = classroomCacheRepository;
    }

    @RabbitListener(queues = "course.updated")
    public void handleCourseUpdated(Map<String, Object> message) {
        Long courseId = Long.parseLong(message.get("courseId").toString());
        String courseName = (String) message.get("courseName");
        String courseCode = (String) message.get("courseCode");

        CourseCache cache = new CourseCache(courseId, courseName, courseCode);
        courseCacheRepository.save(cache);

        examRepository.findByCourseId(courseId).forEach(exam -> {
            exam.setCourseName(courseName);
            examRepository.save(exam);
        });
    }

    @RabbitListener(queues = "course.deleted")
    public void handleCourseDeleted(Map<String, Object> message) {
        Long courseId = Long.parseLong(message.get("courseId").toString());

        examRepository.findByCourseId(courseId).forEach(exam -> {
            exam.setCourseName("corse eliminato");
            examRepository.save(exam);
        });
    }

    @RabbitListener(queues = "classroom.updated")
    public void handleClassroomUpdated(Map<String, Object> message) {
        Long classroomId = Long.parseLong(message.get("classroomId").toString());
        String classroomName = (String) message.get("classroomName");

        ClassroomCache cache = new ClassroomCache(classroomId, classroomName);
        classroomCacheRepository.save(cache);

        examRepository.findByClassroomId(classroomId).forEach(exam -> {
            exam.setClassroomName(classroomName);
            examRepository.save(exam);
        });
    }


}
