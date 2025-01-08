package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.rest.domain.Chat;
import rs.ac.uns.ftn.informatika.rest.domain.Post;

import java.util.List;

public interface IChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c FROM Chat c")
    List<Chat> findAll();


}
