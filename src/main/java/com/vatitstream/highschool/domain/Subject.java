package com.vatitstream.highschool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String category;
    private String description;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mark_id")
    private Mark mark;
}
