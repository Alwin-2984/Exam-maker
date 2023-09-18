/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.form;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author nirmal
 */
public class CandidateForm {

    @NotBlank
    private Integer questinareId;

    @NotBlank
    private String realAnswer;

    public Integer getQuestinareId() {
        return questinareId;
    }

    public void setQuestinareId(Integer questinareId) {
        this.questinareId = questinareId;
    }

    public String getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }

}
