/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.service;

import com.innovaturelabs.training.contacts.form.CandidateForm;
import com.innovaturelabs.training.contacts.view.CandidateDetailedView;

/**
 *
 * @author nirmal
 */
public interface CandidateService {

    CandidateDetailedView add(CandidateForm form);

}
