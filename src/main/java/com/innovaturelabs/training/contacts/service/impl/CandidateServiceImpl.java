/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovaturelabs.training.contacts.entity.Candidate;
import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.entity.User;
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

    @Value("${question.limit}")
    private int questionLimit;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private QuestinareRepository questinareRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public CandidateDetailedView add(List<CandidateForm> forms) {
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
            if (questionnaires == null) {
                throw new BadRequestException("Questionnaire not found for the provided ID and level");
            }

            // Check if their qualification level matches
            if (Objects.equals(questionnaires.getLevel(), userStatus.getLevel())) {

                int score = Objects.equals(questionnaires.getRealAnswer(), form.getRealAnswer()) ? 1 : 0;
                correctAnswers += score;

                // Create the new candidate but do not save it immediately
                Candidate newCandidate = new Candidate(form, score, SecurityUtil.getCurrentUserId());

                // Instead, add the new candidate to a list
                candidateRepository.save(newCandidate);

            } else {
                throw new BadRequestException("invalid qualification level");
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
                case LEVEL4:
                    return new CandidateDetailedView("passed");
                default:
                    break;
            }

            userRepository.save(user);
        }

        return new CandidateDetailedView("success");

    }

    private int getRequiredCorrectAnswers(double targetPercentage, List<CandidateForm> forms) {
        return (int) Math.ceil(targetPercentage * forms.size());
    }

    @Override
    public List<QuestinClientView> list() {
        // Get the current user's level
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());

        int userLevel = userStatus.getLevel().value;

        // Find questions with a level equal to the user's level
        List<Questinare> questionnaires = questinareRepository
                .findQuestionsForCandidate(SecurityUtil.getCurrentUserId(), userLevel);

        return questionnaires.stream()
                .limit(questionLimit)
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
    public TotalPointView totalpoint() {
        Integer userId = SecurityUtil.getCurrentUserId();

        int totalPoints = 0;

        Long questionCount = 0L;

        if (candidateRepository.existsByUserUserId(userId)) {
            totalPoints = candidateRepository.calculateTotalPointsByUserId(userId);
            questionCount = candidateRepository.countTotalQuestionsByUserId(userId);
        }

        return new TotalPointView(userId, totalPoints, questionCount);
    }

}
