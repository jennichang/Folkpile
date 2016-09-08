package com.theironyard.charlotte;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PeopleRepository extends CrudRepository<Person, Integer> {

    List<Person> findAllByGroup(int groupId);


    @Query("SELECT p FROM Person p")
//    @Query("SELECT p FROM people p WHERE p.name LIKE %?1%")
        // need question mark and position of the parameter passed in
    List<Person> findByNameStartsWith(String name); //can have customized queries.

    //use containing in JPA, won't be a custom query
}




