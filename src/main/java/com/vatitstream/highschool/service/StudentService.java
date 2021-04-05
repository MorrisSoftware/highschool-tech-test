package com.vatitstream.highschool.service;

import com.vatitstream.highschool.model.Mark;
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
    private MarkService markService;

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student addMarkToStudent(long studentID, Mark newMark) {
        Student student = this.getStudentByID(studentID);
        student.getMarks().add(newMark);
        Mark mark = markService.create(newMark);
        mark.setStudent(student);
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

    public List<Mark> getMarksByStudent(long student_id) {
        return getStudentByID(student_id).getMarks();
    }

    public Student updateStudent(Student newStudent) {
        Student student = this.getStudentByID(newStudent.getId());
        student.setMarks(newStudent.getMarks());
        student.setClassID(newStudent.getClassID());
        student.setFirstName(newStudent.getFirstName());
        student.setLastName(newStudent.getLastName());
        return studentRepository.save(student);
    }

    public Student updateStudentsMark(long studentID, long markId, Mark newMark) {
        Student student = this.getStudentByID(studentID);
        markService.updateMark(markId, newMark);
        for (int i = 0; i < student.getMarks().size(); i++) {
            if (student.getMarks().get(i).getId() == markId) {
                markService.updateMark(markId, newMark);
                student.getMarks().get(i).setScore(newMark.getScore());
                student.getMarks().get(i).setSubject(newMark.getSubject());
                student.getMarks().get(i).setDate(newMark.getDate());
                return studentRepository.save(student);
            }
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(long studentId) {
        Student student = this.getStudentByID(studentId);
        studentRepository.delete(student);
    }

    public Student deleteMarkFromStudent(long studentId, long markId) {
        Student student = this.getStudentByID(studentId);
        markService.deleteMark(markId);
        for (int i = 0; i < student.getMarks().size(); i++) {
            if (student.getMarks().get(i).getId() == markId) {
                student.getMarks().remove(i);
                return studentRepository.save(student);
            }
        }
        return student;
    }
}
