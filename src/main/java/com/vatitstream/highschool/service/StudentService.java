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

    public List<Mark> getMarksById(long student_id){
        return getStudentbyID(student_id).getMarks();
    }

    public Student addMark(long studentID, Mark mark){
        Student student = this.getStudentbyID(studentID);
        student.getMarks().add(mark);
        mark.setStudent(student);
        markService.create(mark);
        return studentRepository.save(student);
    }
}
