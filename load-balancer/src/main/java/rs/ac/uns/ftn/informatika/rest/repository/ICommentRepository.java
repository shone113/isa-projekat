package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.creationDate >= :startDate")
    List<Comment> findCommentsFromLastWeek(@Param("startDate") LocalDate startDate);

    @Query("SELECT c FROM Comment c WHERE c.creationDate BETWEEN :startDate AND :endDate")
    List<Comment> findCommentsFromDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
