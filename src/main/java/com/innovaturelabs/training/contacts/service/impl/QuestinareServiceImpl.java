/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service.impl;

import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.exception.NotFoundException;
import com.innovaturelabs.training.contacts.form.QuestinareForm;
import com.innovaturelabs.training.contacts.repository.QuestinareRepository;
import com.innovaturelabs.training.contacts.security.util.SecurityUtil;
import com.innovaturelabs.training.contacts.service.QuestinareService;
import com.innovaturelabs.training.contacts.view.QuestinareDetailedView;
import com.innovaturelabs.training.contacts.view.QuestinareListView;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nirmal
 */
@Service
public class QuestinareServiceImpl implements QuestinareService {

    @Autowired
    private QuestinareRepository questinareRepository;

    @Override
    public List<QuestinareDetailedView> list() {
        List<Questinare> questionnaires = questinareRepository.findAllByUserUserId(SecurityUtil.getCurrentUserId());
        return questionnaires.stream()
                .map(QuestinareDetailedView::new)
                .collect(Collectors.toList());
    }

    @Override
    public QuestinareDetailedView add(QuestinareForm form) {
        return new QuestinareDetailedView(
                questinareRepository.save(new Questinare(form, SecurityUtil.getCurrentUserId())));
    }

    public List<QuestinareDetailedView> getQuestionDetail(Integer questionId) {
        List<Questinare> questionnaires = questinareRepository.findById(questionId);
        return questionnaires.stream()
                .map(QuestinareDetailedView::new)
                .collect(Collectors.toList());
    }

    // @Override
    // public ContactDetailView get(Integer contactId) throws NotFoundException {
    // return questinareRepository.findByContactIdAndUserUserId(contactId,
    // SecurityUtil.getCurrentUserId())
    // .map((contact) -> {
    // return new ContactDetailView(contact);
    // }).orElseThrow(NotFoundException::new);
    // }

    // @Override
    // @Transactional
    // public ContactDetailView update(Integer contactId, QuestinareForm form)
    // throws NotFoundException {
    // return questinareRepository.findByContactIdAndUserUserId(contactId,
    // SecurityUtil.getCurrentUserId())
    // .map((contact) -> {
    // return new
    // ContactDetailView(questinareRepository.save(contact.update(form)));
    // }).orElseThrow(NotFoundException::new);
    // }

    // @Override
    // @Transactional
    // public void delete(Integer contactId) throws NotFoundException {
    // questinareRepository.delete(
    // questinareRepository.findByContactIdAndUserUserId(contactId,
    // SecurityUtil.getCurrentUserId())
    // .orElseThrow(NotFoundException::new)
    // );
    // }
}
