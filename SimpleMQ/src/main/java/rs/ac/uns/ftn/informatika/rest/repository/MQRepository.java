package rs.ac.uns.ftn.informatika.rest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.rest.domain.Message;

import java.util.List;

public interface MQRepository extends JpaRepository<Message, Integer> {

    List<Message> findByQueue(String queue);
}
