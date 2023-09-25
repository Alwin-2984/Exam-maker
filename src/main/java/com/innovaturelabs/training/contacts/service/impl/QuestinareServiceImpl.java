/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.entity.User;
import com.innovaturelabs.training.contacts.exception.BadRequestException;
import com.innovaturelabs.training.contacts.form.QuestinareForm;
import com.innovaturelabs.training.contacts.repository.QuestinareRepository;
import com.innovaturelabs.training.contacts.repository.UserRepository;
import com.innovaturelabs.training.contacts.security.util.SecurityUtil;
import com.innovaturelabs.training.contacts.service.QuestinareService;
import com.innovaturelabs.training.contacts.view.QuestinareDetailedView;
import com.innovaturelabs.training.contacts.view.TotalPointView;

/**
 *
 * @author nirmal
 */
@Service
public class QuestinareServiceImpl implements QuestinareService {

    @Autowired
    private QuestinareRepository questinareRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<QuestinareDetailedView> list() {

        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        if (userStatus.getStatus() == 1) {
            List<Questinare> questionnaires = questinareRepository.findAllByUserUserId(
                    SecurityUtil.getCurrentUserId(), Sort.by(Sort.Direction.ASC, "level"));

            return questionnaires.stream()
                    .map(QuestinareDetailedView::new)
                    .collect(Collectors.toList());
        } else {

            List<Questinare> questionnaires = questinareRepository.findAllByLevel(userStatus.getLevel());

            return questionnaires.stream()
                    .map(QuestinareDetailedView::new)
                    .collect(Collectors.toList());

        }

    }

    @Override
    public QuestinareDetailedView add(QuestinareForm form) throws BadRequestException {
        int optionsSize = form.getAnswers().size();
        int realAnswer = form.getRealAnswer();

        if (realAnswer < 1 || realAnswer > optionsSize) {
            throw new BadRequestException("Real answer must be a valid option between 1 and " + optionsSize);
        }

        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        if (userStatus.getStatus() == 1) {
            return new QuestinareDetailedView(

                    questinareRepository.save(new Questinare(form, SecurityUtil.getCurrentUserId())));
        } else {
            throw new BadRequestException("Invalid user");
        }
    }

    public QuestinareDetailedView getQuestionDetail(Integer questionId) {
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        if (userStatus.getStatus() != 1) {
            throw new BadRequestException("Invalid user");

        }
        Questinare questionnaires = questinareRepository.findByQuestinareId(questionId);
        if (questionnaires == null) {
            throw new BadRequestException("Invalid questionId");
        }
        return new QuestinareDetailedView(questionnaires);
    }

    @Override
    public QuestinareDetailedView editQuestionDetails(Integer questionId, QuestinareForm form) {
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        if (userStatus.getStatus() != 1) {
            throw new BadRequestException("Invalid user");

        }
        int optionsSize = form.getAnswers().size();
        int realAnswer = form.getRealAnswer();

        if (realAnswer < 1 || realAnswer > optionsSize) {
            throw new BadRequestException("Real answer must be a valid option between 1 and " + optionsSize);
        }

        Questinare questinare = questinareRepository.findByQuestinareId(questionId);
        questinare.update(form);

        return new QuestinareDetailedView(

                questinareRepository.save(questinare));

    }

    @Override
    @Transactional
    public void deleteQuestion(Integer questionId) {
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        if (userStatus.getStatus() != 1) {
            throw new BadRequestException("Invalid user");

        }
        Questinare questionnaires = questinareRepository.findByQuestinareId(questionId);
        if (questionnaires == null) {
            throw new BadRequestException("Invalid questionId");
        }

        questinareRepository.deleteByQuestinareId(questionId);

    }

}
