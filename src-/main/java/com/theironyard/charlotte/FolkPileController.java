package com.theironyard.charlotte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ben on 9/8/16.
 */
@RestController

public class FolkPileController {

    @Autowired
    PeopleRepository persons;

    @RequestMapping(path = "/person", method = RequestMethod.GET)
    @ResponseBody
    public List<People> getPeople() {
        return (List<People>) persons.findAll();
    }
    @RequestMapping(path = "/person", method = RequestMethod.POST)
    public void addPeople(@RequestBody People person) {
        persons.save(person);
    }
    @RequestMapping(path = "/person", method = RequestMethod.PUT)
    public void updatePeople(@RequestBody People person) {
        persons.save(person);
    }

    @RequestMapping(path = "/person/{id}", method = RequestMethod.DELETE)
    public void deletePeople(@PathVariable("id") int id) {
        persons.delete(id);
    }

    @RequestMapping(path = "/person/{id}", method = RequestMethod.GET)
    public People getPeople(@PathVariable("id") int id) {
        return persons.findOne(id);
    }

}


