package com.vatitstream.highschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "class")
    private String classID;
    @Column(name = "marks")
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "student",targetEntity=Mark.class, cascade = CascadeType.ALL)
    private List<Mark> marks;

    public float calculateAverageMark() {
        float averageMark = 0;
        int numberOfMarks = marks.size();

        if(marks.isEmpty()){
            return 0;
        }
        else{
            for (int i = 0; i < numberOfMarks; i++) {
                averageMark += marks.get(i).getScore();
            }

            return averageMark / numberOfMarks;
        }
    }
}
