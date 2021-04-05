package com.vatitstream.highschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.model.TestScore;
import com.vatitstream.highschool.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    private TestScore testScore;
    private List<TestScore> testScores;
    private String testScoreJson;

    @Before
    public void setup() throws Exception{

        mvc = MockMvcBuilders.standaloneSetup(mockStudentController).build();

        objectMapper = new ObjectMapper();

        testScore = TestScore.builder()
                .id(1)
                .score(100)
                .subject("test")
                .build();

        testScores = new ArrayList<>();
        testScores.add(testScore);
        testScoreJson = objectMapper.writeValueAsString(testScore);

        student = Student.builder()
                .id(1)
                .firstName("Test1")
                .lastName("Test2")
                .classID("10F")
                .testScores(testScores)
                .build();

        studentJson = objectMapper.writeValueAsString(student);

    }

    @Test
    public void addStudentTest() throws Exception {

        when(mockStudentService.addStudent(any())).thenReturn(student);

        mvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andExpect(status().isOk())
                .andExpect(content().json(studentJson));
    }

    @Test
    public void addTestScoreToStudentTest() throws Exception{

        when(mockStudentService.addTestScoreToStudent(anyLong(),any())).thenReturn(student);

        mvc.perform(post("/api/students/1/testscores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testScoreJson))
                .andExpect(status().isOk())
                .andExpect(content().json(studentJson));

        verify(mockStudentService).addTestScoreToStudent(1,testScore);
    }

    @Test
    public void getStudentByIdTest() throws Exception {

        when(mockStudentService.getStudentByID(anyLong())).thenReturn(student);

        mvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(studentJson));

        verify(mockStudentService).getStudentByID(1);
    }

    @Test
    public void getAllStudentsTest() throws Exception{

        List<Student> students = new ArrayList<>();
        students.add(student);
        Page<Student> studentPage = new PageImpl(students);
        String studentsJson = objectMapper.writeValueAsString(students);

        when(mockStudentService.getStudentsPaginated(anyInt(),anyInt())).thenReturn(studentPage);

        mvc.perform(get("/api/students?page=1&size=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(studentsJson));
    }

    @Test
    public void getTestScoresByStudentTest() throws Exception{

        when(mockStudentService.getTestScoresByStudent(anyLong())).thenReturn(testScores);

        mvc.perform(get("/api/students/1/testscores"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testScores)));

        verify(mockStudentService).getTestScoresByStudent(1);
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
    public void updateTestScoreForStudent() throws Exception {

        when(mockStudentService.updateStudentsTestScore(anyLong(),anyLong(),any(TestScore.class))).thenReturn(student);

        mvc.perform(put("/api/students/1/testscores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testScoreJson))
                .andExpect(status().isOk())
                .andExpect(content().json(studentJson));
    }

    @Test
    public void deleteTestScoreFromStudentTest() throws Exception{

        mvc.perform(delete("/api/students/1/testscores/1"))
                .andExpect(status().isOk());

        verify(mockStudentService).deleteTestScoreFromStudent(1,1);
    }

    @Test
    public void deleteStudentTest() throws Exception{

        mvc.perform(delete("/api/students/1"))
                .andExpect(status().isOk());

        verify(mockStudentService).deleteStudent(1);
    }
}
