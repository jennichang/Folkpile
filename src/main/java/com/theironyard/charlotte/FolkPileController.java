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

@RestController //instead of responding as html and accepting form data -- indicates we are interacting with endpoints
//over rest.
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


    @RequestMapping(path = "/people/{groupId}", method = RequestMethod.GET)
    public List<Person> getPeople(@PathVariable("groupId") int groupId) {
        return people.findAllByGroup(groupId);
    }

    @PostConstruct
    public void init() throws FileNotFoundException {
        if (people.count() == 0) { //only parse if the customer repository is empty
            File f = new File("people.csv"); // create new file from csv
            Scanner fileScanner = new Scanner(f); // scanner to read that file
            fileScanner.nextLine(); // ignore first line since is header
            while (fileScanner.hasNext()) { // while there is a token to be read on next line
                String line = fileScanner.nextLine(); //turn that line into a string
                String[] columns = line.split(","); // turn that string into an array, split by comma
                Person personObject = new Person(columns[0], columns[1], columns[2], null);
                people.save(personObject); //save that object to the customer repo
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



