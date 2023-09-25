/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.entity.User.Level;

/**
 *
 * @author nirmal
 */
public interface QuestinareRepository extends CrudRepository<Questinare, Integer> {

    @SuppressWarnings("unchecked")
    Questinare save(Questinare questinare);

    List<Questinare> findAllByUserUserId(Integer currentUserId, Sort sort);

    List<Questinare> findByUserUserId(Integer userId);

    Questinare findByQuestinareId(Integer questionId);

    Questinare findStatusByUserUserId(Integer currentUserId);

    List<Questinare> findAllByLevel(Level level);

    Questinare findByQuestinareIdAndLevel(Integer questinareId, Level level);

    @Query(value = "SELECT DISTINCT q.*, " +
            "       IFNULL(c.answer_status, 0), " +
            "       c.answer_status AS order_answer_status " +
            "FROM questinare q " +
            "LEFT JOIN candidate c " +
            "    ON q.questinare_id = c.questinare_id " +
            "    AND c.user_id = :userId " +
            "WHERE q.level = :level " +
            "  AND ( " +
            "      c.answer_status IS NULL " +
            "      OR c.answer_status <> 1 " +
            "  ) " +
            "  AND NOT EXISTS ( " +
            "      SELECT 1 " +
            "      FROM candidate c2 " +
            "      WHERE c2.questinare_id = q.questinare_id " +
            "        AND c2.answer_status = 1 " +
            "  ) " +
            "ORDER BY " +
            "    CASE " +
            "        WHEN c.answer_status <> 1 THEN 0 " +
            "        WHEN c.answer_status IS NULL THEN RAND() " +
            "        ELSE 1 " +
            "    END, " +
            "    order_answer_status ASC, " +
            "    RAND() ", nativeQuery = true)
    List<Questinare> findQuestionsForCandidate(@Param("userId") Integer userId, @Param("level") int level);

    void deleteByQuestinareId(Integer questionId);

}
