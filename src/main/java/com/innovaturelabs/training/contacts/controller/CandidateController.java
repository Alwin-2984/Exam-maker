/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innovaturelabs.training.contacts.form.CandidateForm;
import com.innovaturelabs.training.contacts.service.CandidateService;
import com.innovaturelabs.training.contacts.view.CandidateDetailedView;
import com.innovaturelabs.training.contacts.view.QuestinClientView;
import com.innovaturelabs.training.contacts.view.TotalPointView;

/**
 *
 * @author nirmal
 */
@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/Questions")
    public List<QuestinClientView> list(Principal p) {
        return candidateService.list();
    }

    @GetMapping("/totalpoint")
    public TotalPointView totalpoint(Principal p) {
        return candidateService.totalpoint();
    }

    @PostMapping
    public CandidateDetailedView add(@RequestBody List<CandidateForm> forms) {
        return candidateService.add(forms);
    }

    @DeleteMapping("/allprogress")
    public void delete() {
        candidateService.delete();
    }

}
