package com.simplemq.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.simplemq.domain.Message;

import java.util.List;

public interface MQRepository extends JpaRepository<Message, Integer> {

    List<Message> findByQueue(String queue);
}
