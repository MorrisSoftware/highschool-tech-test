package com.vatitstream.highschool.controller;

import com.sun.istack.NotNull;
import com.vatitstream.highschool.model.TestScore;
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

    @PostMapping(value = "/{studentId}/testscores", produces = "application/json")
    public @ResponseBody Student addMarktoStudent(@PathVariable long studentId, @RequestBody TestScore newTestScore){
        return studentService.addTestScoreToStudent(studentId, newTestScore);
    }

    @GetMapping(value = "/{studentId}/testscores", produces = "application/json")
    public @ResponseBody List<TestScore> getTestScoresByStudent(@PathVariable Long studentId) {
        return studentService.getTestScoresByStudent(studentId);
    }

    @GetMapping(value = "/{studentId}", produces = "application/json")
    public @ResponseBody Student getStudentById(@PathVariable @NotNull Long studentId) {
        return studentService.getStudentByID(studentId);
    }

    @GetMapping(params = { "page", "size" })
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody List<Student> getAllStudents(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        Page<Student> pageStudents = new PageImpl(
                studentService.getStudentsPaginated(page, size)
                        .stream()
                        .collect(Collectors.toList()));
        return pageStudents.getContent();
    }

    @PutMapping(value = "{studentId}/testscores/{testScoreId}")
    public @ResponseBody Student updateTestScoreForStudent(@PathVariable long studentId, @PathVariable long testScoreId, @RequestBody TestScore testScore){
        return studentService.updateStudentsTestScore(studentId, testScoreId, testScore);
    }

    @PutMapping(value = "")
    public @ResponseBody Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @DeleteMapping(value = "{studentId}/testscores/{testScoreId}")
    public Student  deleteTestScoreFromStudent(@PathVariable long studentId, @PathVariable long testScoreId){
        return studentService.deleteTestScoreFromStudent(studentId, testScoreId);
    }

    @DeleteMapping(value = "/{studentId}")
    public void deleteStudent(@PathVariable long studentId){
        studentService.deleteStudent(studentId);
    }
}
