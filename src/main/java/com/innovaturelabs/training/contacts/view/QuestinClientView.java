/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.view;

import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.entity.User.Level;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nirmal
 */

public class QuestinClientView {
    private Integer questionnaireId;

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
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

    private String question;

    private Level level;
    private List<String> answers;
    private Date createDate;
    private Date updateDate;

    public QuestinClientView(Questinare questionnaire) {
        this.questionnaireId = questionnaire.getQuestinareId();
        this.question = questionnaire.getQuestion();
        this.level = questionnaire.getLevel();
        this.answers = new ArrayList<>(questionnaire.getAnswers());

        this.createDate = questionnaire.getCreateDate();
        this.updateDate = questionnaire.getUpdateDate();
    }

    // Getters and setters for the fields
}
