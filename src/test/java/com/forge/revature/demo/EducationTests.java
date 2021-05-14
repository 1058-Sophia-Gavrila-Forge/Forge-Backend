package com.forge.revature.demo;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.EducationController;
import com.forge.revature.models.Education;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.repo.EducationRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
public class EducationTests {
    private MockMvc mockMvc;
    private Education testEducation;
    private Education testEducation2;
    private Education testEducation3;
    
    @MockBean
    EducationRepo educationRepo;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EducationController(educationRepo)).build();
        User user = new User(1, "Max", "password", true);
        Portfolio portfolio = new Portfolio(1, "My Portfolio", user, false, false, false, "");
        this.testEducation = new Education(1, portfolio, "university", "degree", "graduationDate", 3.5, "");
        this.testEducation2 = new Education(2, portfolio, "uni", "deg", "2021", 2.0, "");
        this.testEducation3 = new Education(3, portfolio, "uni2", "deg2", "2021", 2.0, "");
    }

    @Test
    void testGetAll() throws Exception {
        given(this.educationRepo.findAll()).willReturn(new ArrayList<Education>());

        this.mockMvc.perform(get("/education"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testPost() throws Exception {
        given(this.educationRepo.findById(1)).willReturn(Optional.of(testEducation));
        Education newEducation = new Education("Green River", "AA", "2020", 2.4, "");

        this.mockMvc.perform(post("/education")
            .contentType("application/json;charset=utf-8")
            .content(asJsonString(newEducation)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();

        this.mockMvc.perform(get("/education"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetById() throws Exception {
        given(this.educationRepo.findById(1)).willReturn(Optional.of(testEducation));

        this.mockMvc.perform(get("/education/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testPostById() throws Exception {
        given(this.educationRepo.findById(1)).willReturn(Optional.of(testEducation));

        Education newEducation = new Education("Not a university", "Bachelor's", "2018", 3.5, "");

        this.mockMvc.perform(post("/education/1")
            .contentType("application/json;charset=utf-8")
            .content(asJsonString(newEducation)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void testDelete() throws Exception {
        given(this.educationRepo.findById(1)).willReturn(Optional.of(testEducation));

        this.mockMvc.perform(delete("/education/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();

        this.mockMvc.perform(get("/education/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetByUserId() throws Exception {
        given(this.educationRepo.findByPortfolioUserId(1)).willReturn(Optional.of(testEducation));

        this.mockMvc.perform(get("/education/user/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetByPortfolioId() throws Exception {
        given(this.educationRepo.findByPortfolioId(1)).willReturn(Optional.of(testEducation));

        this.mockMvc.perform(get("/education/portfolio/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    //Needs work, need multiple educations with the same portfolio to see if I can get all educations connected to 1 portfolio/user
    @Test
    void testGetByUserIdAll() throws Exception {
        ArrayList<Education> list = new ArrayList<>();
        list.add(testEducation);
        list.add(testEducation2);
        list.add(testEducation3);
        given(this.educationRepo.findAllByPortfolioUserId(1)).willReturn(list);

        this.mockMvc.perform(get("/education/user/all/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetByPortfolioIdAll() throws Exception {
        ArrayList<Education> list = new ArrayList<>();
        list.add(testEducation);
        list.add(testEducation2);
        list.add(testEducation3);
        given(this.educationRepo.findAllByPortfolioId(1)).willReturn(list);

        this.mockMvc.perform(get("/education/portfolio/all/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }
}
