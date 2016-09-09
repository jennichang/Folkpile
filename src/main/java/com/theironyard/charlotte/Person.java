package com.theironyard.charlotte;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "people")
public class Person {
    @Id // id in our database table
    @GeneratedValue // id is generated for us
            int id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String userName;

//    @Column(nullable = true)
//    Integer groupId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @ManyToMany(mappedBy = "people")
    List<Group> groups = new ArrayList<>();

    public Person() {
    }

    public Person(String firstName, String lastName, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public Person addGroupToPerson(Group g, CrudRepository repo) { //method takes in a group object and crud repo
        groups.add(g); //groups arraylist, add the group
        g.people.add(this); // then in the group object's people arraylist, add this person

        repo.save(this);
        return this;
    }
}


