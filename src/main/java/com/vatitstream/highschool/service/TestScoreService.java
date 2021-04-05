package com.vatitstream.highschool.service;

import com.vatitstream.highschool.model.TestScore;
import com.vatitstream.highschool.repository.TestScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestScoreService {

    @Autowired
    private TestScoreRepository testScoreRepository;

    public TestScore create(TestScore testScore){
        return testScoreRepository.save(testScore);
    }

    public TestScore getMarkById(long markId){
        int id = (int) markId;
        Optional<TestScore> maybeMark = testScoreRepository.findById(id);

        if (maybeMark.isPresent()){
            return maybeMark.get();
        }
        else{
            return null;
        }
    }

    public TestScore updateMark(long oldMarkId, TestScore newTestScore){
        TestScore testScore = this.getMarkById(oldMarkId);

        testScore.setId(newTestScore.getId());
        testScore.setDate(newTestScore.getDate());
        testScore.setScore(newTestScore.getScore());
        testScore.setSubject(newTestScore.getSubject());
        return testScoreRepository.save(testScore);
    }

    public void deleteMark(long markId){
        TestScore testScore = this.getMarkById(markId);
        testScoreRepository.delete(testScore);
    }

}
