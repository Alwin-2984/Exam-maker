/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.innovaturelabs.training.contacts.view.TotalPointView;

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
    @Transactional
    public List<CandidateDetailedView> add(List<CandidateForm> forms) {
        List<CandidateDetailedView> detailedViews = new ArrayList<>();
        int correctAnswers = 0;

        // Get the current user's status and qualification level
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        if (userStatus.getStatus() != 0) {
            throw new BadRequestException("Invalid user status");
        }

        for (CandidateForm form : forms) {

            // Check if a candidate with the same questionnaire ID already exists
            Candidate existingCandidate = candidateRepository
                    .findByQuestinareQuestinareIdAndUserUserIdAndAnswerStatus(form.getQuestinareId(),
                            SecurityUtil.getCurrentUserId(), 1);
            if (existingCandidate != null) {
                throw new BadRequestException(
                        "Candidate with the same questionnaire ID:" + form.getQuestinareId() + " already submitted");
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
            if (Objects.equals(questionnaires.getLevel(), userStatus.getLevel())) {

                int score = Objects.equals(questionnaires.getRealAnswer(), form.getRealAnswer()) ? 1 : 0;
                correctAnswers += score;

                // Create the new candidate but do not save it immediately
                Candidate newCandidate = new Candidate(form, score, SecurityUtil.getCurrentUserId());
                detailedViews.add(new CandidateDetailedView(newCandidate));

                // Instead, add the new candidate to a list
                candidateRepository.save(newCandidate);

            } else {
                throw new BadRequestException(" qualification level");
            }
        }

        int requiredCorrectAnswers;

        switch (forms.size()) {
            case 3:
                requiredCorrectAnswers = getRequiredCorrectAnswers(0.66, forms);
                break;
            case 2:
                requiredCorrectAnswers = getRequiredCorrectAnswers(0.5, forms);
                break;
            case 1:
                requiredCorrectAnswers = forms.size();
                break;
            default:
                requiredCorrectAnswers = getRequiredCorrectAnswers(0.7, forms);
                break;
        }

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

        return detailedViews;
    }

    private int getRequiredCorrectAnswers(double targetPercentage, List<CandidateForm> forms) {
        return (int) Math.ceil(targetPercentage * forms.size());
    }

    @Override
    public List<QuestinClientView> list() {
        // Get the current user's level
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());

        int userLevel = (int) userStatus.getLevel().value;


        // Find questions with a level equal to the user's level
        List<Questinare> questionnaires = questinareRepository
                .findQuestionsForCandidate(SecurityUtil.getCurrentUserId(), userLevel);


        // Map the first 10 questions to QuestinClientView objects
        return questionnaires.stream()
                .limit(10)
                .map(QuestinClientView::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete() {
        // Get the current user's ID
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(currentUserId).orElseThrow(NotFoundException::new);

        user.setLevel(User.Level.LEVEL1);

        userRepository.save(user);

        // Delete all data associated with the current user
        candidateRepository.deleteAllByUserUserId(currentUserId);
    }

    @Override
    public List<TotalPointView> totalpoint() {
        List<TotalPointView> totalPointsList = new ArrayList<>();

        // Get a list of distinct user IDs from the candidate table
        List<Integer> userIds = candidateRepository.findDistinctUserIds();

        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());

        if (userStatus.getStatus() == 0) {
            int totalPoints = candidateRepository.calculateTotalPointsByUserId(SecurityUtil.getCurrentUserId());

            // Create a QuestinClientView object with the user ID and total points
            TotalPointView totalPointView = new TotalPointView(SecurityUtil.getCurrentUserId(), totalPoints);

            // Add the QuestinClientView object to the list
            totalPointsList.add(totalPointView);
        } else {
            for (Integer userId : userIds) {
                // Calculate the total points for each user
                int totalPoints = candidateRepository.calculateTotalPointsByUserId(userId);

                // Create a QuestinClientView object with the user ID and total points
                TotalPointView totalPointView = new TotalPointView(userId, totalPoints);

                // Add the QuestinClientView object to the list
                totalPointsList.add(totalPointView);
            }
        }

        return totalPointsList;
    }

}
