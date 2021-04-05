package com.vatitstream.highschool.controller;

import com.sun.istack.NotNull;
import com.vatitstream.highschool.model.Mark;
import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "", produces = "application/json")
    public @ResponseBody Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PostMapping(value = "/{studentId}/marks", produces = "application/json")
    public @ResponseBody Student addMarktoStudent(@PathVariable long studentId, @RequestBody Mark newMark){
        return studentService.addMarkToStudent(studentId, newMark);
    }

    @GetMapping(value = "/{studentId}/marks", produces = "application/json")
    public @ResponseBody
    List<Mark> getMarksByStudent(@PathVariable Long studentId) {
        return studentService.getMarksByStudent(studentId);
    }

    @GetMapping(value = "/{studentId}", produces = "application/json")
    public @ResponseBody
    Student findStudentById(@PathVariable @NotNull Long studentId) {
        return studentService.getStudentByID(studentId);
    }

    @GetMapping(params = { "page", "size" })
    @CrossOrigin(origins = "http://localhost:9000")
    public @ResponseBody List<Student> findAllStudents(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        Page<Student> pageStudents = new PageImpl(
                studentService.getStudentsPaginated(page, size)
                        .stream()
                        .collect(Collectors.toList()));
        return pageStudents.getContent();
    }

    @PutMapping(value = "{studentId}/marks/{markId}")
    public @ResponseBody Student updateMarkByStudent(@PathVariable long studentId, @PathVariable long markId, @RequestBody Mark mark){
        return studentService.updateStudentsMark(studentId, markId, mark);
    }

    @PutMapping(value = "")
    public @ResponseBody Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @DeleteMapping(value = "{studentId}/marks/{markId}")
    public Student  deleteMarkByStudent(@PathVariable long studentId, @PathVariable long markId){
        return studentService.deleteMarkFromStudent(studentId, markId);
    }

    @DeleteMapping(value = "/{studentId}")
    public void deleteStudent(@PathVariable long studentId){
        studentService.deleteStudent(studentId);
    }
}
