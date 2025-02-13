package com.forge.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.revature.models.Project;
import com.forge.revature.repo.ProjectRepo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/projects")
public class ProjectController {
    @Autowired
    ProjectRepo repo;

    public ProjectController() {
    }

    public ProjectController(ProjectRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Project>> allExperience() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Project>> getExperience(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(repo.findById(id), HttpStatus.OK);
    }

    @GetMapping("/portfolio/{id}")
    public ResponseEntity<List<Project>> portfolioExperience(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(repo.findByPortfolio_Id(id), HttpStatus.OK);
    }

    @PostMapping
    public void createExperience(@RequestBody Project proj) {
        repo.save(proj);
    }

    @PostMapping("/{id}")
    public void updateExperience(@PathVariable(name = "id") long id, @RequestBody Project proj) {
        Optional<Project> update = repo.findById(id);

        if (update.isPresent()) {
            update.get().setName(proj.getName());
            update.get().setDescription(proj.getDescription());
            update.get().setResponsibilities(proj.getResponsibilities());
            update.get().setTechnologies(proj.getTechnologies());
            update.get().setRespositoryUrl(proj.getRespositoryUrl());
            update.get().setWorkProducts(proj.getWorkProducts());
            update.get().setPortfolio(proj.getPortfolio());

            proj = update.get();
            repo.save(proj);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteExperience(@PathVariable(name = "id") long id) {
        repo.deleteById(id);
    }

    @GetMapping("/portfolio/all/{id}")
    public List<Project> getPortfolioProjects(@PathVariable(name = "id") int portfolioId) {
        List<Project> retrievedProjects = repo.findAllByPortfolioId(portfolioId);

        return retrievedProjects;
    }
}