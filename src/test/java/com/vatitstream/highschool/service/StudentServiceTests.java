package com.vatitstream.highschool.service;

import com.vatitstream.highschool.model.TestScore;
import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTests {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository mockStudentRepository;

    @Mock
    TestScoreService mockTestScoreService;

    private Student student_withmarks;
    private Student student_nomarks;
    private TestScore testScore;
    private List<TestScore> testScores;

    @Before
    public void setup(){
        testScore = TestScore.builder()
                .id(1)
                .score(100)
                .subject("test")
                .build();

        testScores = new ArrayList<>();
        testScores.add(testScore);

        student_withmarks = Student.builder()
                .firstName("Test1")
                .lastName("Test2")
                .classID("10F")
                .testScores(testScores)
                .build();

        student_nomarks = Student.builder()
                .firstName("Test1")
                .lastName("Test2")
                .classID("10F")
                .testScores(new ArrayList<TestScore>())
                .build();
    }

    @Test
    public void createTest(){

        studentService.addStudent(student_withmarks);

        verify(mockStudentRepository).save(student_withmarks);
    }

    @Test
    public void getStudentsPaginatedTest(){

        studentService.getStudentsPaginated(1,1);
        Pageable pageableStudents = PageRequest.of(0 , 1);

        verify(mockStudentRepository).findAll(pageableStudents);
    }

    @Test
    public void getStudentByIdTest(){

        long id = 1;
        when(mockStudentRepository.findById(id)).thenReturn(Optional.of(student_withmarks));

        Student resultStudent = studentService.getStudentByID(id);

        assertThat(resultStudent,equalTo(student_withmarks));
    }

    @Test
    public void updateStudent(){

        long testId = 0;
        Student newStudent = Student.builder()
                .id(testId)
                .firstName("newFirst")
                .lastName("newLast")
                .classID("newClass")
                .testScores(testScores)
                .build();

        when(mockStudentRepository.findById(testId)).thenReturn(Optional.of(student_withmarks));
        when(mockStudentRepository.save(any(Student.class))).thenAnswer(new Answer<Student>() {
            @Override
            public Student answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Student) args[0];
            }});

        Student result = studentService.updateStudent(newStudent);

        assertThat(result, equalTo(newStudent));
    }

    @Test
    public void deleteStudent(){

        long testId = 0;
        when(mockStudentRepository.findById(testId)).thenReturn(Optional.of(student_withmarks));

        studentService.deleteStudent(testId);

        verify(mockStudentRepository).delete(eq(student_withmarks));
    }

    @Test
    public void getMarksByStudentTest(){

        long testId = 1;
        when(mockStudentRepository.findById(testId)).thenReturn(Optional.of(student_withmarks));

        List<TestScore> resultTestScores = studentService.getTestScoresByStudent(testId);

        assertThat(resultTestScores, equalTo(student_withmarks.getTestScores()));
    }

    @Test
    public void addMarkToStudentTest(){

        long testId = 1;
        when(mockStudentRepository.findById(testId)).thenReturn(Optional.of(student_nomarks));
        when(mockStudentRepository.save(any(Student.class))).thenAnswer(new Answer<Student>() {
            @Override
            public Student answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Student) args[0];
            }});
        when(mockTestScoreService.create(testScore)).thenReturn(testScore);

        Student resultStudent = studentService.addTestScoreToStudent(testId, testScore);

        assertThat(resultStudent, equalTo(student_withmarks));
    }

    @Test
    public void updateStudentsMarkTest(){

        long testId = 1;
        TestScore newTestScore = TestScore.builder()
                .id(1)
                .score(90)
                .subject("test")
                .build();

        List<TestScore> newMarksList = new ArrayList<>();
        newMarksList.add(newTestScore);

        Student updatedStudent = student_nomarks;
        student_nomarks.setTestScores(newMarksList);

        when(mockStudentRepository.findById(testId)).thenReturn(Optional.of(student_withmarks));
        when(mockTestScoreService.updateMark(testScore.getId(), newTestScore)).thenReturn(newTestScore);
        when(mockStudentRepository.save(any(Student.class))).thenAnswer(new Answer<Student>() {
            @Override
            public Student answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Student) args[0];
        }});

        Student result = studentService.updateStudentsTestScore(testId, testScore.getId(), newTestScore);

        assertThat(result, equalTo(updatedStudent));
    }

    @Test
    public void deleteMarkFromStudentTest(){

        long testId = 1;
        when(mockStudentRepository.findById(testId)).thenReturn(Optional.of(student_withmarks));
        when(mockStudentRepository.save(any(Student.class))).thenAnswer(new Answer<Student>() {
            @Override
            public Student answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Student) args[0];
            }});

        Student resultStudent = studentService.deleteTestScoreFromStudent(testId, testScore.getId());

        assertThat(resultStudent, equalTo(student_nomarks));
    }

}
