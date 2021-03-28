package com.vatitstream.highschool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Student student;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @OneToOne(mappedBy = "mark",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Subject subject;
    private float score;
}
