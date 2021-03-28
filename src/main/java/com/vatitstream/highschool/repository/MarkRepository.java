package com.vatitstream.highschool.repository;

import com.vatitstream.highschool.domain.Mark;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends PagingAndSortingRepository<Mark, Integer> {
}
