package com.vatitstream.highschool;

import com.vatitstream.highschool.model.Mark;
import com.vatitstream.highschool.model.Student;
import com.vatitstream.highschool.model.Subject;
import com.vatitstream.highschool.repository.StudentRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HighschoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HighschoolApplication.class, args);
	}

	@Bean
	ApplicationRunner init(StudentRepository repository){
		return args -> {

			Subject subject = new Subject();
			subject.setCategory("STEM");
			subject.setDescription("Test description");
			subject.setName("Biology");

			LocalDate date = LocalDate.now();
			Mark mark = new Mark();
			mark.setDate(date);
			mark.setScore(10);
			mark.setSubject(subject);

			List<Mark> listMarks = new ArrayList<>();
			listMarks.add(mark);

			Student student = new Student();
			student.setFirstname("TestFirst");
			student.setLastname("TestLast");
			student.setStandard(10);
			student.setMarks(listMarks);
			};
		};
}


