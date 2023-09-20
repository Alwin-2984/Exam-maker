/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.view;

/**
 *
 * @author nirmal
 */

public class TotalPointView {

    private Integer userId;
    private int totalPoints;

    public TotalPointView(Integer userId, int totalPoints) {
        this.userId = userId;
        this.totalPoints = totalPoints;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

}