package com.theironyard.charlotte;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    @ManyToMany
    @JoinTable
    List<Person> people = new ArrayList<>();

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Person> getPeople() {
        return people;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group addPersonToGroup(Person a, CrudRepository repo) {
        people.add(a);
        a.groups.add(this);

        repo.save(this);
        return this;
    }
}