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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class FolkPileApplicationTests {

    @Autowired //start test by creating web application context, we need to pretend we're actually running a web application
            WebApplicationContext wap;

    @Autowired
    PeopleRepository people;


    MockMvc mockMvc; // mock = object that pretends to perform a task, but doesn't really perform that task.
    //can tell mock object to do things you would normally, but it will return a predictable result

    @Before // this will run before our test runs.  initialize build mockmvc object based off of webappcontext
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
    }

    @Test
    @Transactional //defines the scope of a single database transaction. The database transaction happens inside
    // the scope of a persistence context ... but why do we need this in this test but not others?
    public void addPersonToGroups() throws Exception {
        Person p = people.findOne(1);

        int groupCount = p.getGroups().size();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(p); //serializing person object. write the value as a string

        mockMvc.perform(
                MockMvcRequestBuilders.put("/group/{id}", "1")
                        .content(json)
                        .contentType("application/json")
        );

        Assert.assertEquals((groupCount + 1), p.getGroups().size()); // test passes if group size is the original size plus 1
    }


}