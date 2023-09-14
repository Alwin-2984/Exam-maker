/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovaturelabs.training.contacts.entity.Candidate;
import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.entity.User;
import com.innovaturelabs.training.contacts.exception.BadRequestException;
import com.innovaturelabs.training.contacts.form.CandidateForm;
import com.innovaturelabs.training.contacts.repository.CandidateRepository;
import com.innovaturelabs.training.contacts.repository.QuestinareRepository;
import com.innovaturelabs.training.contacts.repository.UserRepository;
import com.innovaturelabs.training.contacts.security.util.SecurityUtil;
import com.innovaturelabs.training.contacts.service.CandidateService;
import com.innovaturelabs.training.contacts.view.CandidateDetailedView;

/**
 *
 * @author nirmal
 */
@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private QuestinareRepository questinareRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CandidateDetailedView add(CandidateForm form) throws BadRequestException {
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        if (userStatus.getStatus() == 0) {
            return new CandidateDetailedView(
                    candidateRepository.save(new Candidate(form, SecurityUtil.getCurrentUserId())));
        } else {
            throw new BadRequestException("Invalid user");
        }

    }

}
