package com.vatitstream.highschool.controller;

import com.vatitstream.highschool.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mark")
public class MarkController {

    @Autowired
    private MarkService markService;


}
