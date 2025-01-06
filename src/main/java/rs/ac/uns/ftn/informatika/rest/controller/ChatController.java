package rs.ac.uns.ftn.informatika.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.ChatDTO;
import rs.ac.uns.ftn.informatika.rest.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import rs.ac.uns.ftn.informatika.rest.service.ChatService;

import java.util.List;

@Tag(name = "Chat controller", description = "The chat API")
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatDTO>> getAll() {
        List<ChatDTO> chatDTOs = chatService.findAll();
        return ResponseEntity.ok(chatDTOs);
    }

    @GetMapping("/for-user/{profileId}")
    public ResponseEntity<List<ChatDTO>> getAllForUser(@PathVariable int profileId) {
        List<ChatDTO> chatDTOs = chatService.findAllForUser(profileId);
        return ResponseEntity.ok(chatDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ChatDTO> getChatById(@PathVariable int id) {
         ChatDTO chatDTO = chatService.findById(id);
        return ResponseEntity.ok(chatDTO);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatDTO> createChat(@RequestBody ChatDTO chatDTO) {
        try{
            ChatDTO savedChat = chatService.create(chatDTO);
            return new ResponseEntity<>(savedChat, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/add-member/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatDTO> addMember(@PathVariable Integer chatId, @RequestParam Integer profileId){
        ChatDTO chatDTO = chatService.addMember(chatId, profileId);
        return ResponseEntity.ok(chatDTO);
    }

    @PutMapping(value = "/remove-member/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatDTO> removeMember(@PathVariable Integer chatId, @RequestParam Integer profileId){
        ChatDTO chatDTO = chatService.removeMember(chatId, profileId);
        return ResponseEntity.ok(chatDTO);
    }
}
