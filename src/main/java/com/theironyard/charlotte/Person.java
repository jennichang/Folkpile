package com.theironyard.charlotte;

import javax.persistence.*;
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

    @ManyToMany
    List<Group> group;


    public Person(String firstName, String lastName, String userName, List<Group> group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.group = group;
    }
}


