package com.theironyard.charlotte;

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
        return "Hello, World!";
    }

    /**
     * Issues to ask Ben:
     * heroku error -- Caused by: org.hibernate.AnnotationException:
        Illegal use of mappedBy on both sides of the relationship: com.theironyard.charlotte.Person.groups

     * can i have null in my saved file? -- i deleted the null now

     * is update completely wrong? -- updated after seeing Ben's example

     * error when running in intellij as well
        org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name
        'org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration': Unsatisfied dependency expressed
        through constructor parameter 0: Error creating bean with name 'dataSource' defined in class path resource
        [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Tomcat.class]: Bean instantiation via factory method
        failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate
        [org.apache.tomcat.jdbc.pool.DataSource]: Factory method 'dataSource'
        threw exception; nested exception is java.lang.IllegalArgumentException: URL must start with 'jdbc';
        nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name
        'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Tomcat.class]:
        Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException:
        Failed to instantiate [org.apache.tomcat.jdbc.pool.DataSource]: Factory method 'dataSource' threw exception; nested exception
        is java.lang.IllegalArgumentException: URL must start with 'jdbc'

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

        //what i originally had
//        Group g = groups.findOne(id);
//        g.people.add(person);
//        groups.save(g); //think this adds a person to the group

//        Person p = people.findOne(person.getId());
//        p.groups.add(groups.findOne(id));
//        people.save(p); //have to update the people table too though right?

        //based on ben's
        Person p = person; //this is the person object to be updated
        Group g = groups.findOne(id); // this is the group object to be updated

        g.addPersonToGroup(p, groups); // adding the person to be updated to the group repo
        p.addGroupToPerson(g, people); // adding the group to the person to be updated in the people repo


//        List<Person> home() {
//            Person p = new Person("Ben"); // created a new person object
//            Address a = new Address("Butts"); // create a new address object
//
//            addresses.save(a); //saved the address object to the addresses table
//            people.save(p); // saved the person object to the people table
//
//            a.addPersonToAddress(p, addresses); // then for that address object, added the person object to the addresses repo
//
//            return (List)people.findAll();
//        }

    }


    // Lindsey's:
//    HashMap<String, ArrayList<Person>> columns = new HashMap<>();
//
//    // read file into memory
//    File f = new File("https://tiycharlotte.slack.com/files/luke/F29JAH1FV/people.csv");
//    Scanner fileScanner = new Scanner(f);
//       fileScanner.nextLine();
//       while (fileScanner.hasNext()) {
//        String line = fileScanner.nextLine();
//        String[] columns = line.split(",");
//        Person person = new Person(Integer.valueOf(columns[0]), columns[1], columns[2], columns[3],
//                columns[4], columns[5]);
//    }


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



