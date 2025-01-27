package com.simplemq.domain;

import javax.persistence.*;

@Entity
@Table(name = "queues")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "message")
    private String message;
    @Column(name = "queue")
    private String queue;

    public Message() {}

    public Message(String message, String queue) {
//        this.id = id;
        this.message = message;
        this.queue = queue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}
