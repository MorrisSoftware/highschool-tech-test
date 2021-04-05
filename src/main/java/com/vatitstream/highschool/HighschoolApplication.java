package com.vatitstream.highschool;

import com.vatitstream.highschool.model.TestScore;
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
			TestScore testScore = new TestScore();
			testScore.setDate(date);
			testScore.setScore(10);
			testScore.setSubject("test");

			List<TestScore> listTestScores = new ArrayList<>();
			listTestScores.add(testScore);

			Student student = new Student();
			student.setFirstName("TestFirst");
			student.setLastName("TestLast");
			student.setClassID("10F");
			student.setTestScores(listTestScores);
			};
		};
}


