package com.vatitstream.highschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatitstream.highschool.model.Mark;
import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    @Mock
    StudentService mockStudentService;

    @InjectMocks
    StudentController mockStudentController;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    private Student student;
    private String studentJson;

    private Mark mark;
    private List<Mark> marks;
    private String markJson;

    @Before
    public void setup() throws Exception{

        mvc = MockMvcBuilders.standaloneSetup(mockStudentController).build();

        objectMapper = new ObjectMapper();

        mark = Mark.builder()
                .id(1)
                .score(100)
                .subject("test")
                .build();

        marks = new ArrayList<>();
        marks.add(mark);
        markJson = objectMapper.writeValueAsString(mark);

        student = Student.builder()
                .id(1)
                .firstName("Test1")
                .lastName("Test2")
                .classID("10F")
                .marks(marks)
                .build();

        studentJson = objectMapper.writeValueAsString(student);

    }

    @Test
    public void addStudentTest() throws Exception {

        mvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andExpect(status().isOk());

        verify(mockStudentService).addStudent(student);
    }

    @Test
    public void addMarkToStudentTest() throws Exception{

        when(mockStudentService.addMarkToStudent(anyLong(),any())).thenReturn(student);

        mvc.perform(post("/api/students/1/marks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(markJson))
                .andExpect(status().isOk())
                .andExpect(content().json(studentJson));
    }

    @Test
    public void getMarksByStudentTest() throws Exception{

        mvc.perform(get("/api/students/1/marks"))
                .andExpect(status().isOk());

        verify(mockStudentService).getMarksByStudent(1);
    }

    @Test
    public void updateStudentTest() throws Exception {

        Student newStudent = Student.builder()
                .id(1)
                .firstName("Test1")
                .lastName("Test2")
                .classID("10F")
                .build();

        String newJSON = objectMapper.writeValueAsString(newStudent);

        mvc.perform(put("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newJSON))
                .andExpect(status().isOk());

        verify(mockStudentService).updateStudent(newStudent);
    }

    @Test
    public void deleteStudentTest() throws Exception{
        mvc.perform(delete("/api/students/1"))
                .andExpect(status().isOk());

        verify(mockStudentService).deleteStudent(1);
    }



}
