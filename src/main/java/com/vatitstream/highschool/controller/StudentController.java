package com.vatitstream.highschool.controller;

import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "", produces = "application/json")
    public @ResponseBody
    Student addStudent(@RequestBody Student student) {
        Student test = student;
        return studentService.create(test);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public @ResponseBody
    Student findStudentById(@PathVariable long studentId) {
        return studentService.getStudentByFirstName(studentId);
    }

    @GetMapping(params = { "page", "size" })
    public @ResponseBody List<Student> findAllStudents(@RequestParam("page") int page,
                                                   @RequestParam("size") int size) {
        Page<Student> pageStudents = new PageImpl(
                studentService.getStudentsPaginated(page, size)
                        .stream()
                        .collect(Collectors.toList()));
        return pageStudents.getContent();
    }
}
