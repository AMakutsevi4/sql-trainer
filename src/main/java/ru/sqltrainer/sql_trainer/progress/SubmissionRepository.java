package ru.sqltrainer.sql_trainer.progress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    long countByUserIdAndTaskId(Long userId, String taskId);

    boolean existsByUserIdAndTaskIdAndPassedTrue(Long userId, String taskId);

    Optional<Submission> findFirstByUserIdAndTaskIdOrderByCreatedAtDesc(Long userId, String taskId);

    @Query("""
            select distinct s.taskId from Submission s
            where s.user.id = :userId and s.passed = true and s.taskId in :taskIds
            """)
    Set<String> findSolvedTaskIds(@Param("userId") Long userId, @Param("taskIds") Collection<String> taskIds);

    List<Submission> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("""
            select s.user.id, count(s), max(s.createdAt)
            from Submission s
            group by s.user.id
            """)
    List<Object[]> attemptAggregatesByUser();

    @Query("""
            select s.user.id, count(distinct s.taskId)
            from Submission s
            where s.passed = true
            group by s.user.id
            """)
    List<Object[]> solvedAggregatesByUser();
}
