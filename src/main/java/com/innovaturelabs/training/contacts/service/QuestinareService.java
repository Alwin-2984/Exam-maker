/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service;

import java.util.List;

import javax.validation.Valid;

import com.innovaturelabs.training.contacts.form.QuestinareForm;
import com.innovaturelabs.training.contacts.view.QuestinareDetailedView;

/**
 *
 * @author nirmal
 */
public interface QuestinareService {

    List<QuestinareDetailedView> list();

    QuestinareDetailedView add(QuestinareForm form);

    QuestinareDetailedView getQuestionDetail(Integer questionId);

    QuestinareDetailedView editQuestionDetails(Integer questionId,QuestinareForm form);

    void deleteQuestion(Integer questionId);

}
