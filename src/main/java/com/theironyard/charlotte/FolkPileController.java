package com.theironyard.charlotte;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class FolkPileController {
    @Autowired
    PeopleRepository people;

    @Autowired
    GroupRepository groups;

    @RequestMapping(path = "/")
    @ResponseBody
    String home() {
        return "Homepage For FolkPile";
    }

    // Return either all people, or search of people
    @RequestMapping(path = "/people", method = RequestMethod.GET)
    public List<Person> getPeople(String search) {
        if (search != null) {
            return (List<Person>) people.findByNameStartsWith(search); // apparently this casting is redundant?
        }
        return (List<Person>) people.findAll();
    }

    //Get all groups
    @RequestMapping(path = "/group", method = RequestMethod.GET)
    public List<Group> getGroup() {
        return (List<Group>) groups.findAll();
    }

    //Get specific groups
    @RequestMapping(path = "/group/{id}", method = RequestMethod.GET)
    public Group getSpecificGroup(@PathVariable("id") int id) {
        return groups.findOne(id);
    }

    //Add person to group
    @CrossOrigin
    @RequestMapping(path = "/group/{id}", method = RequestMethod.PUT)
    public void addPersonToGroup(@PathVariable("id") int id, @RequestBody Person person) {

        Person p = people.findOne(person.id); //this is the person object to be updated
        Group g = groups.findOne(id); // this is the group object to be updated

        if (!p.getGroups().contains(g)) { // so one person cannot have the same group added multiple times
            g.addPersonToGroup(p, groups); // adding the person to be updated to the group repo
            //p.addGroupToPerson(g, people); // adding the group to the person to be updated in the people repo; redundant
        }

//        //what i originally had
//                Group g = groups.findOne(id);
//                g.people.add(person);
//                groups.save(g); //think this adds a person to the group
//
//                Person p = people.findOne(person.getId());
//                p.groups.add(groups.findOne(id));
//                people.save(p); //have to update the people table too though right?
    }


    @PostConstruct
    public void init() throws FileNotFoundException {
        if (people.count() == 0) {
            File f = new File("people.csv");
            Scanner fileScanner = new Scanner(f);
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] columns = line.split(",");
                Person personObject = new Person(columns[0], columns[1], columns[2]);
                people.save(personObject);
            }
        }
        if (groups.count() == 0) {
            Group lumberjack = new Group("Lumberjacks Club");
            groups.save(lumberjack);
            Group friends = new Group("Friends");
            groups.save(friends);
            Group pb = new Group("Potential Babysitters");
            groups.save(pb);
        }

    }
}



