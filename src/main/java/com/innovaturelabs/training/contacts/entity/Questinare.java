/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.entity;

import com.innovaturelabs.training.contacts.entity.User.Level;
import com.innovaturelabs.training.contacts.form.QuestinareForm;
import java.util.Collection;
import java.util.Date;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nirmal
 */
@Entity
public class Questinare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questinareId;
    private String question;
    private String realAnswer;
    private Level level;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @ElementCollection(fetch = FetchType.LAZY)
    private Collection<String> answers;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Questinare(Integer questinareId, String question, Level level, User user, Collection<String> answers,
            Date createDate, Date updateDate) {
        this.questinareId = questinareId;
        this.question = question;
        this.level = level;
        this.user = user;
        this.answers = answers;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Questinare() {
    }

    public Questinare(Integer questinareId) {
        this.questinareId = questinareId;
    }

    public Questinare(QuestinareForm form, Integer userId) {


        this.user = new User(userId);
        this.question = form.getQuestion();
        this.realAnswer = form.getRealAnswer();
        this.level = form.getLevel();
        this.answers = form.getAnswers();
        Date dt = new Date();
        this.createDate = dt;
        this.updateDate = dt;

    }

    public Questinare update(QuestinareForm form) {
        this.question = form.getQuestion();
        this.level = form.getLevel();
        this.answers = form.getAnswers();

        Date dt = new Date();
        this.updateDate = dt;

        return this;
    }

    public Integer getQuestinareId() {
        return questinareId;
    }

    public void setQuestinareId(Integer questinareId) {
        this.questinareId = questinareId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<String> answers) {
        this.answers = answers;
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
        return "Questinare [questinareId=" + questinareId + ", question=" + question + ", level=" + level + ", user="
                + user + ", answers=" + answers + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
    }

    public String getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }

}
