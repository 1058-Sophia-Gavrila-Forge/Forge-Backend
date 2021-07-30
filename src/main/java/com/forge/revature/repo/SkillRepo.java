package com.forge.revature.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forge.revature.models.Matrix;
import com.forge.revature.models.Skill;

public interface SkillRepo extends JpaRepository<Skill, Integer> {
	public List<Skill> findAllByMatrix(Matrix m);
}