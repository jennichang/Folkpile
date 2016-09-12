package com.theironyard.charlotte;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    @ManyToMany
    @JoinTable
    //@JsonBackReference
    //@JsonIgnore
    List<Person> people = new ArrayList<>();


    public List<Person> getPeople() {
        return people;
    }

    public Group(String name) {
        this.name = name;
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

    public Group() {
    }

    public Group addPersonToGroup(Person a, CrudRepository repo) {
        people.add(a);
        a.groups.add(this);

        repo.save(this);
        return this;
    }
}