/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service;

import com.innovaturelabs.training.contacts.exception.NotFoundException;
import com.innovaturelabs.training.contacts.form.QuestinareForm;
import com.innovaturelabs.training.contacts.view.QuestinareDetailedView;
import com.innovaturelabs.training.contacts.view.QuestinareListView;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author nirmal
 */
public interface QuestinareService {

    List<QuestinareDetailedView> list();

    QuestinareDetailedView add(QuestinareForm form);

    List<QuestinareDetailedView> getQuestionDetail(Integer questionId);

    // ContactDetailView get(Integer contactId) throws NotFoundException;

    // ContactDetailView update(Integer contactId, QuestinareForm form) throws
    // NotFoundException;

    // void delete(Integer contactId) throws NotFoundException;
}
