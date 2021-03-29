package com.vatitstream.highschool.service;

import com.vatitstream.highschool.model.Mark;
import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private StudentService studentService;

    public Mark create(Mark mark, int studentID){
        Student student = studentService.getStudentbyID(studentID);
        mark.setStudent(student);
        student = studentService.addMarkToStudent(studentID, mark);
        mark.setStudent(student);
        return markRepository.save(mark);
    }

}
