package com.theironyard.charlotte;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class FolkPileApplicationTests {

    @Autowired //start test by created web application context, run our tests, need to pretened
            // we're actually running a web application
            WebApplicationContext wap;

    @Autowired
    PeopleRepository people;

    @Autowired
    PeopleRepository groups;


    MockMvc mockMvc; // mock = object taht preteneds to perform a task, but doesn't really perform that task.
    //can tell mock object to do things you would normally, but it will return a predictable result

    @Test
    public void updatePerson() throws Exception { // test addition of a user
        Person person = new Person(); //make new user object
        person.setFirstName("Jennifer"); // use setters to set fields
        person.setLastName("Chang");
        person.setUserName("blah@gmail.com");

        ObjectMapper mapper = new ObjectMapper(); //object mapper = part of spring framework to build json objects for testing
        // basically gson for spring
        String json = mapper.writeValueAsString(person); //serializing user object. write the value as a string

        mockMvc.perform( //performing a mvc request to web application using mockmvc field
                MockMvcRequestBuilders.post("/people") // if you want to build a request start with mockmvcrequestbuilders
                        .content(json)
                        .contentType("application/json")
        );

        Assert.assertTrue(people.count() == 1);
        // after post data to user endpoint, we know we should have 1 user in database, if we do we succeeded
    }

    @Before // this will run before our test runs.  initialize build mockmvc object based off of webappcontext
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
    }

}