package com.theironyard.charlotte;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id // id in our database table
    @GeneratedValue // id is generated for us
            int id;

    @Column(nullable = false)
    String name;

    @ManyToMany(mappedBy = "groups")
    List<Person> people = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public Group addPersonToGroup(Person a, CrudRepository repo) {
        people.add(a);
        a.groups.add(this);

        repo.save(this);
        return this;
    }
}