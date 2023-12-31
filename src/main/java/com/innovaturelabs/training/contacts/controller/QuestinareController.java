/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innovaturelabs.training.contacts.form.QuestinareForm;
import com.innovaturelabs.training.contacts.service.QuestinareService;
import com.innovaturelabs.training.contacts.view.QuestinareDetailedView;

/**
 *
 * @author nirmal
 */
@RestController
@RequestMapping("/questinare")
public class QuestinareController {

    @Autowired
    private QuestinareService questinareService;

    @GetMapping
    public List<QuestinareDetailedView> list(Principal p) {
        return questinareService.list();
    }

    @PostMapping
    public QuestinareDetailedView add(@Valid @RequestBody QuestinareForm form) {
        return questinareService.add(form);
    }

    @GetMapping("/{questionId}")
    public QuestinareDetailedView getQuestionDetail(@PathVariable("questionId") Integer questionId) {
        return questinareService.getQuestionDetail(questionId);
    }

    @PutMapping("/{questionId}")
    public QuestinareDetailedView editQuestionDetails(@PathVariable("questionId") Integer questionId,
            @Valid @RequestBody QuestinareForm form) {
        return questinareService.editQuestionDetails(questionId, form);
    }

    @DeleteMapping("/{questionId}")
    public void deleteQuestion(@PathVariable("questionId") Integer questionId) {
        questinareService.deleteQuestion(questionId);
    }

}
