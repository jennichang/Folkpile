package com.theironyard.charlotte;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PeopleRepository extends CrudRepository<Person, Integer> {

    //List<Person> findAllByName(String name);


    @Query("SELECT p FROM Person p WHERE p.firstName LIKE %?1%")
    List<Person> findByNameStartsWith(String firstName); //can have customized queries.

    //use containing in JPA, won't be a custom query
}




