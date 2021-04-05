package com.vatitstream.highschool.service;

import com.vatitstream.highschool.model.Mark;
import com.vatitstream.highschool.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;

    public Mark create(Mark mark){
        return markRepository.save(mark);
    }

    public Mark getMarkById(long markId){
        int id = (int) markId;
        Optional<Mark> maybeMark = markRepository.findById(id);

        if (maybeMark.isPresent()){
            return maybeMark.get();
        }
        else{
            return null;
        }
    }

    public Mark updateMark(long oldMarkId, Mark newMark){
        Mark mark = this.getMarkById(oldMarkId);

        mark.setId(newMark.getId());
        mark.setDate(newMark.getDate());
        mark.setScore(newMark.getScore());
        mark.setSubject(newMark.getSubject());
        return markRepository.save(mark);
    }

    public void deleteMark(long markId){
        Mark mark = this.getMarkById(markId);
        markRepository.delete(mark);
    }

}
