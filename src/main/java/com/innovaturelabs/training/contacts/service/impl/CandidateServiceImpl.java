/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public List<CandidateDetailedView> add(List<CandidateForm> forms) {
        List<CandidateDetailedView> detailedViews = new ArrayList<>();

        for (CandidateForm form : forms) {
            // Get the current user's status and qualification level
            User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());

            // Check if a candidate with the same questionnaire ID already exists
            Boolean candidateExists = candidateRepository.findByQuestinareQuestinareId(form.getQuestinareId())
                    .orElse(false);
            if (Boolean.TRUE.equals(candidateExists)) {
                throw new BadRequestException("Candidate with the same questionnaire ID already submitted");
            }

            // Retrieve the questionnaire associated with the form's questinareId
            Questinare questionnaires = questinareRepository.findByQuestinareIdAndLevel(
                    form.getQuestinareId(), userStatus.getLevel());

            // Check if the user's status is active and their qualification level matches
            // the questionnaire's level

            if (userStatus.getStatus() == 0 && Objects.equals(questionnaires.getLevel(), userStatus.getLevel())) {
                int score = Objects.equals(questionnaires.getRealAnswer(), form.getRealAnswer()) ? 1 : 0;

                // Create and save the new candidate
                Candidate newCandidate = new Candidate(form, score, SecurityUtil.getCurrentUserId());
                Candidate savedCandidate = candidateRepository.save(newCandidate);

                // Create a detailed view for the saved candidate
                CandidateDetailedView detailedView = new CandidateDetailedView(savedCandidate);
                detailedViews.add(detailedView);
            } else {
                throw new BadRequestException("Invalid user status or qualification level");
            }
        }

        return detailedViews;
    }

}
