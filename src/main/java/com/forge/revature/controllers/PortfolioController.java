package com.forge.revature.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.models.AboutMe;
import com.forge.revature.models.Certification;
import com.forge.revature.models.Education;
import com.forge.revature.models.Equivalency;
import com.forge.revature.models.FullPortfolio;
import com.forge.revature.models.GitHub;
import com.forge.revature.models.Honor;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Project;
import com.forge.revature.models.WorkExperience;
import com.forge.revature.models.WorkHistory;
import com.forge.revature.repo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/portfolios")
public class PortfolioController {
    @Autowired
    PortfolioRepo portRepo;

    @Autowired
    AboutMeRepo aboutMeRepo;

    @Autowired
    CertificationRepo certificationRepo;

    @Autowired
    EducationRepo educationRepo;

    @Autowired
    EquivalencyRepo equivalencyRepo;

    @Autowired
    GitHubRepo gitHubRepo;

    @Autowired
    HonorRepo honorRepo;

    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    WorkExperienceRepo workExperienceRepo;

    @Autowired
    WorkHistoryRepo workHistoryRepo;

    public PortfolioController() {
    }

    public PortfolioController(PortfolioRepo portRepo) {
        this.portRepo = portRepo;
    }

    public PortfolioController(PortfolioRepo portRepo, AboutMeRepo aboutMeRepo, CertificationRepo certificationRepo,
            EducationRepo educationRepo, EquivalencyRepo equivalencyRepo, GitHubRepo gitHubRepo, HonorRepo honorRepo,
            ProjectRepo projectRepo, WorkExperienceRepo workExperienceRepo, WorkHistoryRepo workHistoryRepo) {
        this.portRepo = portRepo;
        this.aboutMeRepo = aboutMeRepo;
        this.certificationRepo = certificationRepo;
        this.educationRepo = educationRepo;
        this.equivalencyRepo = equivalencyRepo;
        this.gitHubRepo = gitHubRepo;
        this.honorRepo = honorRepo;
        this.projectRepo = projectRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.workHistoryRepo = workHistoryRepo;
    }

    @GetMapping
    public List<Portfolio> getAll(){
        List<Portfolio> ports = StreamSupport.stream(portRepo.findAll().spliterator(), false)
        .collect(Collectors.toList());
    return ports;
    }

    @GetMapping("/{id}")
    public Portfolio getByID(@PathVariable(name = "id") int id){
        return portRepo.findById(id).get();
    }

    @GetMapping("/users/all/{id}")
    public List<Portfolio> getPortfoliosByUserId(@PathVariable int id){
        List<Portfolio> portfolios = portRepo.findAllByUserId(id);
        return portfolios;
    }

    @PostMapping
    public Portfolio postPort(@RequestBody Portfolio port){
        return portRepo.save(port);
    }
    @PostMapping("/{id}")
    public void updateUser(@PathVariable int id , @RequestBody Portfolio updated){
        Optional<Portfolio> old = portRepo.findById(id);

        if(old.isPresent()){
            old.get().setApproved(updated.isApproved());
            old.get().setFeedback(updated.getFeedback());
            old.get().setName(updated.getName());
            old.get().setReviewed(updated.isReviewed());
            old.get().setSubmitted(updated.isSubmitted());
            old.get().setUser(updated.getUser());
        
            portRepo.save(old.get());
        }
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deletePortfolio(@PathVariable int id) throws ResourceNotFoundException{
        Optional<Portfolio> port = portRepo.findById(id);

        if(port.isPresent()){
            portRepo.delete(port.get());
        }else{
            throw new ResourceNotFoundException("The Portfolio to be deleted could not be found");
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping(value = "/full/{id}", produces = "application/octet-stream")
    public ResponseEntity<ByteArrayResource> getFullPortfolio(@PathVariable int id, HttpServletResponse response) throws JsonProcessingException {
        if (!portRepo.existsById(id)) return null;
        Portfolio port = portRepo.findById(id).get();
        FullPortfolio full = new FullPortfolio(
            id,
            port.getName(),
            port.getUser(),
            port.isSubmitted(),
            port.isApproved(),
            port.isReviewed(),
            port.getFeedback(),
            aboutMeRepo.findByPortfolioId(id).get(),
            certificationRepo.findAllByPortfolioId(id),
            educationRepo.findAllByPortfolioId(id),
            equivalencyRepo.findAllByPortfolioId(id),
            gitHubRepo.findByPortfolio(port),
            honorRepo.findByPortfolio(port),
            projectRepo.findByPortfolio_Id(id),
            workExperienceRepo.findByPortfolio_Id(id),
            workHistoryRepo.findByPortfolio(port)
        );
        response.setHeader("Content-Disposition", "attachment; filename=Portfolio-" + id + ".json");
        return new ResponseEntity<ByteArrayResource>(new ByteArrayResource(new ObjectMapper().writeValueAsString(full).getBytes()), HttpStatus.OK);
    }

    @PostMapping(value = "/full", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void postFullPortfolio(@RequestBody FullPortfolio fullPortfolio){
        Portfolio newPortfolio = new Portfolio(
            fullPortfolio.getId(),
            fullPortfolio.getName(),
            fullPortfolio.getUser(),
            fullPortfolio.isSubmitted(),
            fullPortfolio.isApproved(),
            fullPortfolio.isReviewed(),
            fullPortfolio.getFeedback()
        );
        AboutMe newAboutMe = new AboutMe(
            fullPortfolio.getAboutMe().getBio(),
            fullPortfolio.getAboutMe().getEmail(),
            fullPortfolio.getAboutMe().getPhone()
        );
        List<Certification> newCertificationsList = fullPortfolio.getCertifications();
        List<Education> newEducationsList = fullPortfolio.getEducations();
        List<Equivalency> newEquivalencyList = fullPortfolio.getEquivalencies();
        GitHub newGitHub = fullPortfolio.getGitHubs();//Change to list when available
        List<Honor> newHonorsList = fullPortfolio.getHonors();
        List<Project> newProjectsList = fullPortfolio.getProjects();
        // User newUser = fullPortfolio.getUser();
        List<WorkExperience> newWorkExperiencesList = fullPortfolio.getWorkExperiences();
        List<WorkHistory> newWorkHistoryList = fullPortfolio.getWorkHistories();

        portRepo.save(newPortfolio);
        aboutMeRepo.save(newAboutMe);
        certificationRepo.saveAll(newCertificationsList);
        educationRepo.saveAll(newEducationsList);
        equivalencyRepo.saveAll(newEquivalencyList);
        gitHubRepo.save(newGitHub);//Change to saveAll when list
        honorRepo.saveAll(newHonorsList);
        projectRepo.saveAll(newProjectsList);
        workExperienceRepo.saveAll(newWorkExperiencesList);
        workHistoryRepo.saveAll(newWorkHistoryList);

    }

}
