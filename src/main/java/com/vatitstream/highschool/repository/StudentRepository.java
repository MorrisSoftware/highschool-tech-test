package com.vatitstream.highschool.repository;

import com.vatitstream.highschool.domain.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
}
