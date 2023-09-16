/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.controller;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
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
    public List<CandidateDetailedView> add(@RequestBody List<CandidateForm> forms) {
        return candidateService.add(forms);
    }
}