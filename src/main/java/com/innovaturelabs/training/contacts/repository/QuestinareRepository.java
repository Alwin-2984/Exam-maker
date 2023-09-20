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

    @SuppressWarnings("unchecked")
    Questinare save(Questinare questinare);

    List<Questinare> findAllByUserUserId(Integer currentUserId);

    List<Questinare> findByUserUserId(Integer userId);

    List<Questinare> findByQuestinareId(Integer questionId);

    Questinare findStatusByUserUserId(Integer currentUserId);

    List<Questinare> findAllByLevel(Level level);

    Questinare findByQuestinareIdAndLevel(Integer questinareId, Level level);

    @Query(value = "SELECT q.* FROM questinare q " +
            "LEFT JOIN candidate c ON q.questinare_id = c.questinare_id " +
            "AND c.user_id = :userId " +
            "WHERE q.level = :level " +
            "AND (c.answer_status IS NULL OR c.answer_status != 1)" +
            "ORDER BY (c.answer_status != 1) DESC, RAND()", nativeQuery = true)
    List<Questinare> findQuestionsForCandidate(Integer userId,int level);

}
