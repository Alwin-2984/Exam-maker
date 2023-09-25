package com.innovaturelabs.training.contacts.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.innovaturelabs.training.contacts.entity.Candidate;

public interface CandidateRepository extends CrudRepository<Candidate, Integer> {

    List<Candidate> findAllByUserUserId(Integer currentUserId);

    List<Candidate> findAllByUserUserIdAndAnswerStatus(Integer currentUserId, int i);

    List<Candidate> findByUserUserId(Integer userId);

    Candidate findByQuestinareQuestinareId(Integer questinareId);

    Candidate findByQuestinareQuestinareIdAndUserUserId(Integer questinareId, Integer currentUserId);

    void deleteByUserUserId(Integer currentUserId);

    void deleteAllByUserUserId(Integer currentUserId);

    @Query("SELECT SUM(c.answerStatus) FROM Candidate c WHERE c.user.userId = :userId")
    Integer calculateTotalPointsByUserId(Integer userId);

    @Query("SELECT COUNT(c) FROM Candidate c WHERE c.user.userId = :userId")
    Long countTotalQuestionsByUserId(@Param("userId") Integer userId);

    Candidate findByQuestinareQuestinareIdAndUserUserIdAndAnswerStatus(Integer questinareId, Integer currentUserId,
            int i);

    @Query(value = "SELECT COUNT(*) AS failed_attempt_count FROM candidate WHERE user_id = :userId   AND questinare_id = :questionnaireId    AND answer_status = 0;", nativeQuery = true)
    Long countFailedAttemptsByUserIdAndQuestionnaireId(Integer userId, Integer questionnaireId);

    boolean existsByUserUserId(Integer userId);
}
