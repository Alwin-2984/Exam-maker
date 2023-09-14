/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innovaturelabs.training.contacts.form.CandidateForm;
import com.innovaturelabs.training.contacts.service.CandidateService;
import com.innovaturelabs.training.contacts.view.CandidateDetailedView;

/**
 *
 * @author nirmal
 */
@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public CandidateDetailedView add( @RequestBody CandidateForm form) {
        return candidateService.add(form);
    }

}
