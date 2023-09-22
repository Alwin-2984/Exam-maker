/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.form;

import java.util.Collection;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.innovaturelabs.training.contacts.entity.User.Level;

/**
 *
 * @author nirmal
 */
public class QuestinareForm {

    @NotBlank
    private String question;

    @NotNull
    private Level level;

    @NotNull
    private int realAnswer;

    @NotNull
    private Collection<@NotBlank @Size(max = 255) String> answers;



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

    public Collection<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<String> answers) {
        this.answers = answers;
    }

    public int getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(int realAnswer) {
        this.realAnswer = realAnswer;
    }

 

}
