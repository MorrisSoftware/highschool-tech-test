package com.vatitstream.highschool.controller;

import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TestStudentController {

    @Mock
    StudentService mockStudentService;

    @Mock
    StudentController mockStudentController;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(mockStudentController).build();
    }

    @Test
    public void testAddStudent() throws Exception {
        Student student = Student.builder()
                .firstname("Test1")
                .lastname("Test2")
                .standard(1)
                .build();

        mockStudentController.addStudent(student);

        mvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstname\" : \"Test1\",\"lastname\": \"Test2\",\"standard\": 1 }"))
                .andExpect(status().isOk());

    }

}
