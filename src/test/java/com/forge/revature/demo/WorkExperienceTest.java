package com.forge.revature.demo;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.WorkExperienceController;
import com.forge.revature.models.WorkExperience;
import com.forge.revature.repo.WorkExperienceRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class WorkExperienceTest {

    private MockMvc mock;

    @MockBean
    WorkExperienceRepo repo;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setup() {
        mock = MockMvcBuilders.standaloneSetup(new WorkExperienceController(repo)).build();
    }
    
    @Test
    void testGetAll() throws Exception {
        given(repo.findAll()).willReturn(new ArrayList<WorkExperience>());

        mock.perform(get("/workexperience"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetById() throws Exception {
        given(repo.findById((long) 1)).willReturn(Optional.of(new WorkExperience("Walmart", "Software developer",
                "sample responsibilities", "sample description", "sample technologies", format.parse("2017-08-28"),
                format.parse("2020-02-07"))));

        mock.perform(get("/workexperience/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testCreate() throws Exception {
        WorkExperience work = new WorkExperience("Walmart", "Software developer",
                "sample responsibilities", "sample description", "sample technologies", format.parse("2017-08-28"),
                format.parse("2020-02-07"));
        work.setId(1);
        given(repo.save(work)).willReturn(work);

        mock.perform(post("/workexperience").contentType("application/json;charset=utf-8").content(new ObjectMapper().writeValueAsString(work)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void testUpdate() throws Exception {
        WorkExperience work = new WorkExperience("Walmart", "Software developer",
                "sample responsibilities", "sample description", "sample technologies", format.parse("2017-08-28"),
                format.parse("2020-02-07"));
        given(repo.findById((long) 1)).willReturn(Optional.of(work));

        WorkExperience newWork = new WorkExperience("Walmart", "Software developer",
                "different responsibilities", "different description", "new technologies", format.parse("2017-08-28"),
                format.parse("2020-02-07"));

        mock.perform(post("/workexperience/1").contentType("application/json;charset=utf-8").content(new ObjectMapper().writeValueAsString(newWork)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void testDelete() throws Exception {
        mock.perform(delete("/workexperience/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }
}
