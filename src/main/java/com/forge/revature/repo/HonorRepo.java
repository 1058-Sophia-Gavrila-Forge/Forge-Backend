package com.forge.revature.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forge.revature.models.Honor;
import com.forge.revature.models.Portfolio;

@Repository
public interface HonorRepo extends JpaRepository<Honor, Integer>{
  List<Honor> findByPortfolio(Portfolio portfolio);
}