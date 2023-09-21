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
import org.springframework.stereotype.Service;

import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.entity.User;
import com.innovaturelabs.training.contacts.exception.BadRequestException;
import com.innovaturelabs.training.contacts.form.QuestinareForm;
import com.innovaturelabs.training.contacts.repository.QuestinareRepository;
import com.innovaturelabs.training.contacts.repository.UserRepository;
import com.innovaturelabs.training.contacts.security.util.SecurityUtil;
import com.innovaturelabs.training.contacts.service.QuestinareService;
import com.innovaturelabs.training.contacts.view.QuestinareDetailedView;

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
            List<Questinare> questionnaires = questinareRepository.findAllByUserUserId(SecurityUtil.getCurrentUserId());

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
        int a = form.getAnswers().size();
        for (String answer : form.getAnswers()) {
            if (!Objects.equals(answer, form.getRealAnswer())) {
                a = a - 1;
                if (a == 0) {
                    throw new BadRequestException("real answer must match options");
                }
            }
        }
        User userStatus = userRepository.findStatusByUserId(SecurityUtil.getCurrentUserId());
        if (userStatus.getStatus() == 1) {
            return new QuestinareDetailedView(

                    questinareRepository.save(new Questinare(form, SecurityUtil.getCurrentUserId())));
        } else {
            throw new BadRequestException("Invalid user");
        }
    }

    public List<QuestinareDetailedView> getQuestionDetail(Integer questionId) {
        List<Questinare> questionnaires = questinareRepository.findByQuestinareId(questionId);
        return questionnaires.stream()
                .map(QuestinareDetailedView::new)
                .collect(Collectors.toList());
    }

}
