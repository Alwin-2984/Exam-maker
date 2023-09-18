/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovaturelabs.training.contacts.entity.Candidate;
import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.entity.User;
import com.innovaturelabs.training.contacts.entity.User.Level;
import com.innovaturelabs.training.contacts.exception.BadRequestException;
import com.innovaturelabs.training.contacts.exception.NotFoundException;
import com.innovaturelabs.training.contacts.form.CandidateForm;
import com.innovaturelabs.training.contacts.repository.CandidateRepository;
import com.innovaturelabs.training.contacts.repository.QuestinareRepository;
import com.innovaturelabs.training.contacts.repository.UserRepository;
import com.innovaturelabs.training.contacts.security.util.SecurityUtil;
import com.innovaturelabs.training.contacts.service.CandidateService;
import com.innovaturelabs.training.contacts.view.CandidateDetailedView;
import com.innovaturelabs.training.contacts.view.QuestinClientView;

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
        int correctAnswers = 0;

        // Get the current user's status and qualification level
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        List<Candidate> candidatesToSave = new ArrayList<>(); // Collect new candidates here
        for (CandidateForm form : forms) {
            // Check if a candidate with the same questionnaire ID already exists
            Candidate existingCandidate = candidateRepository.findByQuestinareQuestinareId(form.getQuestinareId());
            if (existingCandidate != null) {
                throw new BadRequestException("Candidate with the same questionnaire ID already submitted");
            }

            // Retrieve the questionnaire associated with the form's questinareId
            Questinare questionnaires = questinareRepository.findByQuestinareIdAndLevel(
                    form.getQuestinareId(), userStatus.getLevel());

            // Check if the retrieved questionnaire is null and throw a BadRequestException
            // if it is
            if (questionnaires == null) {
                throw new BadRequestException("Questionnaire not found for the provided ID and level");
            }

            // Check if the user's status is active and their qualification level matches
            // the questionnaire's level
            if (userStatus.getStatus() == 0 && Objects.equals(questionnaires.getLevel(), userStatus.getLevel())) {
                int score = Objects.equals(questionnaires.getRealAnswer(), form.getRealAnswer()) ? 1 : 0;
                correctAnswers += score;

                // Create the new candidate but do not save it immediately
                Candidate newCandidate = new Candidate(form, score, SecurityUtil.getCurrentUserId());
                detailedViews.add(new CandidateDetailedView(newCandidate));

                // Instead, add the new candidate to a list
                candidatesToSave.add(newCandidate);
            } else {
                throw new BadRequestException("Invalid user status or qualification level");
            }
        }

        int requiredCorrectAnswers = (int) Math.ceil(0.7 * forms.size());

        if (correctAnswers >= requiredCorrectAnswers) {
            User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(NotFoundException::new);

            switch (user.getLevel()) {
                case LEVEL1:
                    user.setLevel(User.Level.LEVEL2);
                    break;
                case LEVEL2:
                    user.setLevel(User.Level.LEVEL3);
                    break;
                case LEVEL3:
                    user.setLevel(User.Level.LEVEL4);
                    break;
                default:
                    break;
            }

            userRepository.save(user);
        }
        // Loop through and save each candidate individually
        for (Candidate candidate : candidatesToSave) {
            candidateRepository.save(candidate);
        }

        return detailedViews;
    }

    @Override
    public List<QuestinClientView> list() {
        // Get the current user's level
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        Level userLevel = userStatus.getLevel();

        // Find questions with a level equal to the user's level
        List<Questinare> questionnaires = questinareRepository.findAllByLevel(userLevel);

        // Get a list of questinare_ids that the user has already answered
        List<Integer> answeredQuestionIds = candidateRepository.findAllByUserUserId(SecurityUtil.getCurrentUserId())
                .stream()
                .map(candidate -> candidate.getQuestinare().getQuestinareId()) // Assuming Candidate has a reference to
                                                                               // Questinare
                .collect(Collectors.toList());

        // Filter out questions that the user has already answered
        List<Questinare> filteredQuestionnaires = questionnaires.stream()
                .filter(questionnaire -> !answeredQuestionIds.contains(questionnaire.getQuestinareId()))
                .limit(10)
                .collect(Collectors.toList());

        // Map the first 10 questions to QuestinClientView objects
        return filteredQuestionnaires.stream()
                .map(QuestinClientView::new)
                .collect(Collectors.toList());
    }

}