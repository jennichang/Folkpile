package com.theironyard.charlotte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

@RestController
public class FolkPileController {
    @Autowired
    PeopleRepository people;

    @Autowired
    GroupRepository groups;

//    @RequestMapping(path = "/")
//    @ResponseBody
//    String home() {
//        return "Hello, World!";
//    }

    /** Issues to ask Ben
     * heroku error with people is not mapped
     * can i have null in my saved file?
     * is update completely wrong?
     * error when running in intellij as well (error starting application context)
     * search will have a url like this, correct? "/people/?=oli"
     */


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

    //Add person to group -- and group to person
    @RequestMapping(path = "/group/{id}", method = RequestMethod.PUT)
    public void addPersonToGroup(@PathVariable("id") int id, @RequestBody Person person) {
        Group g = groups.findOne(id);
        g.person.add(person);
        groups.save(g); //think this adds a person to the group

        Person p = people.findOne(person.getId());
        p.group.add(groups.findOne(id));
        people.save(p); //have to update the people table too though right?
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
                Person personObject = new Person(columns[0], columns[1], columns[2], null); // can i have a null here?
                people.save(personObject);
            }
        }
        if (groups.count() == 0) {
            Group lumberjack = new Group("Lumberjacks Club", null);
            groups.save(lumberjack);
            Group friends = new Group("Friends", null);
            groups.save(friends);
            Group pb = new Group("Potential Babysitters", null);
            groups.save(pb);
        }


    }
}



