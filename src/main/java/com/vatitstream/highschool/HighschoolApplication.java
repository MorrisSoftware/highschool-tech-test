package com.vatitstream.highschool;

import com.vatitstream.highschool.model.Mark;
import com.vatitstream.highschool.model.Student;
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
			LocalDate date = LocalDate.now();
			Mark mark = new Mark();
			mark.setDate(date);
			mark.setScore(10);
			mark.setSubject("test");

			List<Mark> listMarks = new ArrayList<>();
			listMarks.add(mark);

			Student student = new Student();
			student.setFirstName("TestFirst");
			student.setLastName("TestLast");
			student.setClassID("10F");
			student.setMarks(listMarks);
			};
		};
}


