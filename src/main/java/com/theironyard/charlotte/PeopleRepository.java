package com.theironyard.charlotte;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PeopleRepository extends CrudRepository<Person, Integer> {

    //List<Person> findAllByName(String name);


    @Query("SELECT p FROM person p WHERE p.first_name LIKE %?1%")
    List<Person> findByNameStartsWith(String firstName); //can have customized queries.

    //use containing in JPA, won't be a custom query
}




