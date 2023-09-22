/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.innovaturelabs.training.contacts.form.CandidateForm;

/**
 *
 * @author nirmal
 */
@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer candidateId;

    private int realAnswer;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    private int answerStatus;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Questinare questinare;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Candidate(int realAnswer, User user, Date createDate, Date updateDate) {
        this.realAnswer = realAnswer;
        this.user = user;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Candidate() {
    }

    public Candidate(CandidateForm form, int i, Integer userId) {

        this.user = new User(userId);
        this.questinare = new Questinare(form.getQuestinareId());
        this.realAnswer = form.getRealAnswer();
        this.answerStatus = i;
        Date dt = new Date();
        this.createDate = dt;
        this.updateDate = dt;

    }

    public Candidate update(CandidateForm form) {
        this.realAnswer = form.getRealAnswer();

        Date dt = new Date();
        this.createDate = dt;
        this.updateDate = dt;

        return this;
    }

    public int getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(int realAnswer) {
        this.realAnswer = realAnswer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return ", realAnswer=" + realAnswer + ", user=" + user
                + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Questinare getQuestinare() {
        return questinare;
    }

    public void setQuestinare(Questinare questinare) {
        this.questinare = questinare;
    }

    public int getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(int answerStatus) {
        this.answerStatus = answerStatus;
    }



}
