package com.vatitstream.highschool.service;

import com.vatitstream.highschool.model.TestScore;
import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestScoreService testScoreService;

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student addTestScoreToStudent(long studentID, TestScore newTestScore) {
        Student student = this.getStudentByID(studentID);
        student.getTestScores().add(newTestScore);
        TestScore testScore = testScoreService.create(newTestScore);
        testScore.setStudent(student);
        return studentRepository.save(student);
    }

    public Student getStudentByID(long id) {
        Optional<Student> maybeStudent = studentRepository.findById(id);

        if (maybeStudent.isPresent()) {
            return maybeStudent.get();
        }
        return null;
    }

    public Page<Student> getStudentsPaginated(int page, int size) {
        Pageable pageableStudents = PageRequest.of(page - 1, size);
        Page<Student> studentPage = studentRepository.findAll(pageableStudents);
        return studentPage;
    }

    public List<TestScore> getTestScoresByStudent(long student_id) {
        return getStudentByID(student_id).getTestScores();
    }

    public Student updateStudent(Student newStudent) {
        Student student = this.getStudentByID(newStudent.getId());
        student.setTestScores(newStudent.getTestScores());
        student.setClassID(newStudent.getClassID());
        student.setFirstName(newStudent.getFirstName());
        student.setLastName(newStudent.getLastName());
        return studentRepository.save(student);
    }

    public Student updateStudentsTestScore(long studentID, long markId, TestScore newTestScore) {
        Student student = this.getStudentByID(studentID);
        testScoreService.updateMark(markId, newTestScore);
        for (int i = 0; i < student.getTestScores().size(); i++) {
            if (student.getTestScores().get(i).getId() == markId) {
                testScoreService.updateMark(markId, newTestScore);
                student.getTestScores().get(i).setScore(newTestScore.getScore());
                student.getTestScores().get(i).setSubject(newTestScore.getSubject());
                student.getTestScores().get(i).setDate(newTestScore.getDate());
                return studentRepository.save(student);
            }
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(long studentId) {
        Student student = this.getStudentByID(studentId);
        studentRepository.delete(student);
    }

    public Student deleteTestScoreFromStudent(long studentId, long markId) {
        Student student = this.getStudentByID(studentId);
        testScoreService.deleteMark(markId);
        for (int i = 0; i < student.getTestScores().size(); i++) {
            if (student.getTestScores().get(i).getId() == markId) {
                student.getTestScores().remove(i);
                return studentRepository.save(student);
            }
        }
        return student;
    }
}
