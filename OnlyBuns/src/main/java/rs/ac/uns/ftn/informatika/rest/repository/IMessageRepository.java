package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.rest.domain.Message;

import java.util.List;

public interface IMessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m " +
            "WHERE m.chat.id = :chatId " +
            "ORDER BY m.creationDate DESC")
    List<Message> findMessagesForChat(@Param("chatId") Integer chatId);

}
