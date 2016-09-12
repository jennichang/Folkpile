package com.theironyard.charlotte;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "people")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String userName;


    @ManyToMany(mappedBy = "people")
            //@JsonBackReference
    List<Group> groups = new ArrayList<>();

    public Person() {
    }

    public Person(String firstName, String lastName, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public int getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Person addGroupToPerson(Group g, CrudRepository repo) { //method takes in a group object and crud repo
        groups.add(g); //groups arraylist, add the group
        g.people.add(this); // then in the group object's people arraylist, add this person

        repo.save(this);
        return this;
    }
}


