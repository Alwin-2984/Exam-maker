/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.entity.User.Level;

/**
 *
 * @author nirmal
 */
public interface QuestinareRepository extends CrudRepository<Questinare, Integer> {

    Questinare save(Questinare questinare);

    List<Questinare> findAllByUserUserId(Integer currentUserId);

    List<Questinare> findByUserUserId(Integer userId);

    List<Questinare> findByQuestinareId(Integer questionId);

    Questinare findStatusByUserUserId(Integer currentUserId);

    List<Questinare> findAllByLevel(Level level);

    Questinare findByQuestinareIdAndLevel(Integer questinareId, Level level);

    @Query("SELECT q FROM Questinare q LEFT JOIN Candidate c ON q.questinareId = c.questinare.questinareId AND c.user.userId = :userId WHERE q.level = :level AND (c.answerStatus IS NULL OR c.answerStatus != 1)")
    List<Questinare> findQuestionsForCandidate(Integer userId, Level level);

}
