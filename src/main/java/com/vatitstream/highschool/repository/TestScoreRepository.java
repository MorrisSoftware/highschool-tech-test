package com.vatitstream.highschool.repository;

import com.vatitstream.highschool.model.TestScore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestScoreRepository extends PagingAndSortingRepository<TestScore, Integer> {
}
