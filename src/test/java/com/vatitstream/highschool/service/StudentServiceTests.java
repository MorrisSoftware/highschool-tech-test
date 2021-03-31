package com.vatitstream.highschool.service;

import com.vatitstream.highschool.model.Mark;
import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.model.Subject;
import com.vatitstream.highschool.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTests {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository mockStudentRepository;

    @Mock
    MarkService mockMarkService;

    private Student student;
    private Student student_nomarks;
    private Mark mark;
    private List<Mark> marks;
    private Subject subject;

    @Before
    public void setup(){
        subject = Subject.builder()
                .id(1)
                .name("Test")
                .category("Test")
                .description("Test")
                .build();

        mark = Mark.builder()
                .id(1)
                .score(100)
                .subject(subject)
                .build();

        marks = new ArrayList<>();
        marks.add(mark);

        student = Student.builder()
                .firstname("Test1")
                .lastname("Test2")
                .standard(1)
                .marks(marks)
                .build();

        student_nomarks = Student.builder()
                .firstname("Test1")
                .lastname("Test2")
                .standard(1)
                .marks(new ArrayList<Mark>())
                .build();


    }

    @Test
    public void createTest(){

        studentService.create(student);

        verify(mockStudentRepository).save(student);
    }

    @Test
    public void getStudentsPaginatedTest(){

        Page<Student> result = studentService.getStudentsPaginated(1,1);
        Pageable pageableStudents = PageRequest.of(0 , 1);
        verify(mockStudentRepository).findAll(pageableStudents);

    }

    @Test
    public void getMarksByIdTest(){
        long testId = 1;

        when(mockStudentRepository.findById(testId)).thenReturn(Optional.of(student));

        List<Mark> resultMarks = studentService.getMarksById(testId);

        assertThat(resultMarks, equalTo(student.getMarks()));
    }


    @Test
    public void addMarkToStudentTest(){
        long testId = 1;

        when(mockStudentRepository.findById(testId)).thenReturn(Optional.of(student_nomarks));
        when(mockStudentRepository.save(student_nomarks)).thenReturn(student);
        when(mockMarkService.create(testId, mark)).thenReturn(mark);

        Student resultStudent = studentService.addMarkToStudent(testId, mark);

        assertThat(resultStudent, equalTo(student));
    }


    /*getMarksById
    addMark*/

}
