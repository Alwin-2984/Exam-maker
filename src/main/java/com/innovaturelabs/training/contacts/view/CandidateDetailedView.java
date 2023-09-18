/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.view;

import com.innovaturelabs.training.contacts.entity.Candidate;

import java.util.Date;

/**
 *
 * @author nirmal
 */

public class CandidateDetailedView {

    public String getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    private Integer questinareId;
    private Integer userId;

    private String realAnswer;

    private Date createDate;
    private Date updateDate;

    public CandidateDetailedView(Candidate candidate) {
        this.questinareId = candidate.getQuestinare().getQuestinareId();
        this.realAnswer = candidate.getRealAnswer();
this.userId = candidate.getUser().getUserId();
        this.createDate = candidate.getCreateDate();
        this.updateDate = candidate.getUpdateDate();
        this.realAnswer = candidate.getRealAnswer();
    }

    public Integer getQuestinareId() {
        return questinareId;
    }

    public void setQuestinareId(Integer questinareId) {
        this.questinareId = questinareId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
