/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.repository;

import com.innovaturelabs.training.contacts.entity.Questinare;
import com.innovaturelabs.training.contacts.entity.User.Level;
import com.innovaturelabs.training.contacts.view.QuestinareDetailedView;
import com.innovaturelabs.training.contacts.view.QuestinareListView;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/**
 *
 * @author nirmal
 */
public interface QuestinareRepository extends Repository<Questinare, Integer> {

    Questinare save(Questinare questinare);

    List<Questinare> findAllByUserUserId(Integer currentUserId);

    List<Questinare> findByUserUserId(Integer userId);

    List<Questinare> findById(Integer questionId);

    Questinare findStatusByUserUserId(Integer currentUserId);


    List<Questinare> findAllByLevel(Level level);

    Questinare findByQuestinareIdAndLevel(Integer questinareId, Level level);

}
