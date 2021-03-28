package com.vatitstream.highschool.web;

import com.vatitstream.highschool.domain.Mark;
import com.vatitstream.highschool.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mark")
public class MarkController {

    @Autowired
    private MarkService markService;

    @PostMapping(value = "", produces = "application/json")
    public @ResponseBody
    Mark create(@RequestBody Mark mark){
        return markService.create(mark);
    }
}
