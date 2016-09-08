package com.theironyard.charlotte;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id // id in our database table
    @GeneratedValue // id is generated for us
            int id;

    @Column(nullable = false)
    String name;

    @ManyToMany
    List<Person> person;

    public Group(String name, List<Person> person) {
        this.name = name;
        this.person = person;
    }
}