/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.entity;

import com.innovaturelabs.training.contacts.form.QuestinareForm;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
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

    public static enum Status {
        DELETED((byte) 0),
        ACTIVE((byte) 1);

        public final byte value;

        private Status(byte value) {
            this.value = value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questinareId;
    private String question;
    private String realAnswer;

    private byte level;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @ElementCollection(fetch = FetchType.LAZY)
    private Collection<String> answers;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Questinare(Integer questinareId, String question, byte level, User user, Collection<String> answers,
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

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((questinareId == null) ? 0 : questinareId.hashCode());
        result = prime * result + ((question == null) ? 0 : question.hashCode());
        result = prime * result + level;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((answers == null) ? 0 : answers.hashCode());
        result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
        result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Questinare other = (Questinare) obj;
        if (questinareId == null) {
            if (other.questinareId != null)
                return false;
        } else if (!questinareId.equals(other.questinareId))
            return false;
        if (question == null) {
            if (other.question != null)
                return false;
        } else if (!question.equals(other.question))
            return false;
        if (level != other.level)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (answers == null) {
            if (other.answers != null)
                return false;
        } else if (!answers.equals(other.answers))
            return false;
        if (createDate == null) {
            if (other.createDate != null)
                return false;
        } else if (!createDate.equals(other.createDate))
            return false;
        if (updateDate == null) {
            if (other.updateDate != null)
                return false;
        } else if (!updateDate.equals(other.updateDate))
            return false;
        return true;
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
