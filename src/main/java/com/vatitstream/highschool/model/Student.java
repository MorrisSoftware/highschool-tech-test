package com.vatitstream.highschool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstname;
    private String lastname;
    private int standard;
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Mark> marks;
}
