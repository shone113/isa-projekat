package rs.ac.uns.ftn.informatika.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.domain.Message;
import rs.ac.uns.ftn.informatika.rest.dto.MessageDTO;
import rs.ac.uns.ftn.informatika.rest.service.MessageService;


import java.util.List;

@Tag(name = "Message controller", description = "The message API")
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public MessageController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<MessageDTO>> getAllForChat(@PathVariable Integer chatId) {
        List<MessageDTO> messages = messageService.getAllForChat(chatId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{chatId}/{profileId}")
    public ResponseEntity<List<MessageDTO>> getAllForChat(@PathVariable Integer chatId, @PathVariable Integer profileId) {
        List<MessageDTO> messages = messageService.getAllForUserProfile(chatId, profileId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        try{
            MessageDTO newMessage = messageService.create(messageDTO);
            return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

//    @MessageMapping("/dm/{chatId}")
//    public void handleDirectMessage(@DestinationVariable String chatId, MessageDTO message) {
//        // Logika za direktne poruke
//        messagingTemplate.convertAndSend("/socket-publisher/dm/" + chatId, message);
//    }
//
//    @MessageMapping("/group/{chatId}")
//    public void handleGroupMessage(@DestinationVariable String chatId, MessageDTO message) {
//        // Logika za grupne poruke
//        messagingTemplate.convertAndSend("/socket-publisher/group/" + chatId, message);
//    }

}
