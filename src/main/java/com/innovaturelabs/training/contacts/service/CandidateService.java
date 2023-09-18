/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service;

import java.util.List;

import com.innovaturelabs.training.contacts.form.CandidateForm;
import com.innovaturelabs.training.contacts.view.CandidateDetailedView;
import com.innovaturelabs.training.contacts.view.QuestinareDetailedView;

/**
 *
 * @author nirmal
 */
public interface CandidateService {

    List<CandidateDetailedView> add(List<CandidateForm> form);

    List<QuestinareDetailedView> list();

}
