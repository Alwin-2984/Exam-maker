/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service;

import java.util.List;

import com.innovaturelabs.training.contacts.form.CandidateForm;
import com.innovaturelabs.training.contacts.view.CandidateDetailedView;
import com.innovaturelabs.training.contacts.view.QuestinClientView;
import com.innovaturelabs.training.contacts.view.TotalPointView;

/**
 *
 * @author nirmal
 */
public interface CandidateService {

    CandidateDetailedView add(List<CandidateForm> form);

    List<QuestinClientView> list();

    void delete();

    TotalPointView totalpoint();

}
