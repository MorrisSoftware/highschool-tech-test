package com.vatitstream.highschool.service;

import com.vatitstream.highschool.model.Mark;
import com.vatitstream.highschool.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;

    public Mark create(Mark mark){
        return markRepository.save(mark);
    }

}
