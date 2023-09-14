/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.innovaturelabs.training.contacts.entity.Candidate;

/**
 *
 * @author nirmal
 */
public interface CandidateRepository extends Repository<Candidate, Integer> {

    Candidate save(Candidate candidate);

    List<Candidate> findAllByUserUserId(Integer currentUserId);

    List<Candidate> findByUserUserId(Integer userId);

    List<Candidate> findById(Integer questionId);

}
