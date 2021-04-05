package com.vatitstream.highschool.service;

import com.vatitstream.highschool.model.Mark;
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
    MarkService mockMarkService;

    private Student student_withmarks;
    private Student student_nomarks;
    private Mark mark;
    private List<Mark> marks;

    @Before
    public void setup(){
        mark = Mark.builder()
                .id(1)
                .score(100)
                .subject("test")
                .build();

        marks = new ArrayList<>();
        marks.add(mark);

        student_withmarks = Student.builder()
                .firstName("Test1")
                .lastName("Test2")
                .classID("10F")
                .marks(marks)
                .build();

        student_nomarks = Student.builder()
                .firstName("Test1")
                .lastName("Test2")
                .classID("10F")
                .marks(new ArrayList<Mark>())
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
                .marks(marks)
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

        List<Mark> resultMarks = studentService.getMarksByStudent(testId);

        assertThat(resultMarks, equalTo(student_withmarks.getMarks()));
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
        when(mockMarkService.create(mark)).thenReturn(mark);

        Student resultStudent = studentService.addMarkToStudent(testId, mark);

        assertThat(resultStudent, equalTo(student_withmarks));
    }

    @Test
    public void updateStudentsMarkTest(){

        long testId = 1;
        Mark newMark = Mark.builder()
                .id(1)
                .score(90)
                .subject("test")
                .build();

        List<Mark> newMarksList = new ArrayList<>();
        newMarksList.add(newMark);

        Student updatedStudent = student_nomarks;
        student_nomarks.setMarks(newMarksList);

        when(mockStudentRepository.findById(testId)).thenReturn(Optional.of(student_withmarks));
        when(mockMarkService.updateMark(mark.getId(),newMark)).thenReturn(newMark);
        when(mockStudentRepository.save(any(Student.class))).thenAnswer(new Answer<Student>() {
            @Override
            public Student answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Student) args[0];
        }});

        Student result = studentService.updateStudentsMark(testId,mark.getId(),newMark);

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

        Student resultStudent = studentService.deleteMarkFromStudent(testId, mark.getId());

        assertThat(resultStudent, equalTo(student_nomarks));
    }

}
