/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.view;

import com.innovaturelabs.training.contacts.json.Json;


import java.util.Date;

/**
 *
 * @author nirmal
 */
public class QuestinareListView {

    private final int questinareId;
    private final String question;
    private final byte level;
    @Json.DateTimeFormat
    private final Date createDate;
    @Json.DateTimeFormat
    private final Date updateDate;

    public QuestinareListView(int questinareId, String question, byte level, Date createDate, Date updateDate) {
        this.questinareId = questinareId;
        this.question = question;
        this.level = level;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public int getQuestinareId() {
        return questinareId;
    }

    public String getQuestion() {
        return question;
    }

    public byte getLevel() {
        return level;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

}
