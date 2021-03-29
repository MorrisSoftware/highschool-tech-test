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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student create(Student student){
        return studentRepository.save(student);
    }

    public Student getStudentbyID(long id){
        Optional<Student> maybeStudent = studentRepository.findById(id);

        if (maybeStudent.isPresent()){
            return maybeStudent.get();
        }
        return null;
    }

    public Page<Student> getStudentsPaginated(int page, int size){
        Pageable pageableStudents = PageRequest.of(page -1 , size);
        Page<Student> studentPage = studentRepository.findAll(pageableStudents);
        return studentPage;
    }

    public Student addMarkToStudent(int studentID, Mark mark){
        Student student = getStudentbyID(studentID);
        student.getMarks().add(mark);
        return studentRepository.save(student);
    }
}
