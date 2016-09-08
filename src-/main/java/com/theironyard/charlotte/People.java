package com.theironyard.charlotte;

import javax.persistence.*;

/**
 * Created by meekinsworks on 9/8/16.
 */

@Entity
@Table(name = "persons")
    public class People {
        @Id
        @GeneratedValue
        int id;

        @Column(nullable = false)
        String firstName;

        @Column(nullable = false)
        String lastName;

        @Column(nullable = false)
        String userName;

        @Column
        int group;

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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }


}

