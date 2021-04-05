package com.vatitstream.highschool.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    @Mock
    StudentService mockStudentService;

    @InjectMocks
    StudentController mockStudentController;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(mockStudentController).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    public void testAddStudent() throws Exception {
        Student student = Student.builder()
                .firstName("Test1")
                .lastName("Test2")
                .classID("10F")
                .build();
        String inputJson = this.mapToJson(student);

        mvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isOk());

        verify(mockStudentService).addStudent(student);
    }

    @Test
    public void testUpdateStudent() throws Exception {

        Student newStudent = Student.builder()
                .id(1)
                .firstName("Test1")
                .lastName("Test2")
                .classID("10F")
                .build();

        String newJSON = this.mapToJson(newStudent);

        mvc.perform(put("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newJSON))
                .andExpect(status().isOk());

        verify(mockStudentService).updateStudent(newStudent);
    }

    @Test
    public void testDeleteStudent() throws Exception{
        mvc.perform(delete("/api/students/1"))
                .andExpect(status().isOk());

        verify(mockStudentService).deleteStudent(1);
    }

}
