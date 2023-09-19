package com.innovaturelabs.training.contacts.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.innovaturelabs.training.contacts.entity.Candidate;

public interface CandidateRepository extends CrudRepository<Candidate, Integer> {

    List<Candidate> findAllByUserUserId(Integer currentUserId);

    List<Candidate> findByUserUserId(Integer userId);


    Candidate findByQuestinareQuestinareId(Integer questinareId);

    Candidate findByQuestinareQuestinareIdAndUserUserId(Integer questinareId, Integer currentUserId);

    void deleteByUserUserId(Integer currentUserId);

    void deleteAllByUserUserId(Integer currentUserId);

    // Custom query to find distinct user IDs
    @Query("SELECT DISTINCT c.user.userId FROM Candidate c")
    List<Integer> findDistinctUserIds();

    // Custom query to calculate total points for a user
    @Query("SELECT SUM(c.answerStatus) FROM Candidate c WHERE c.user.userId = :userId")
    Integer calculateTotalPointsByUserId(Integer userId);
}


// SELECT *
// FROM questinare q
// LEFT JOIN candidate c ON q.questinare_id = c.questinare_id
// WHERE (c.answer_status IS NULL OR c.answer_status = 0)
// AND q.level = 0; -- Adjust the level as needed