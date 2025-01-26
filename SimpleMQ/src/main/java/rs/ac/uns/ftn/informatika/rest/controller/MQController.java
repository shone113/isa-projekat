package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.informatika.rest.domain.Message;
import rs.ac.uns.ftn.informatika.rest.service.MQService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/simplemq")
public class MQController {
    @Autowired
    private MQService mqService;

    @PostMapping("/produce")
    public ResponseEntity produce(@RequestBody String message, String queue) {
        Message mess =  mqService.add(new Message(message, queue));
        if(mess != null)
            return ResponseEntity.ok().body(mess);
        else
            return ResponseEntity.badRequest().build();
    }


    @GetMapping("/consume")
    public ResponseEntity<String> consume(String queue) {
        List<Message> messages = mqService.getAllByQueue(queue);
        if(messages != null && messages.size() > 0) {
            Message message = messages.get(0);
            mqService.remove(message.getId());
            return ResponseEntity.ok(message.getMessage());
        }
        else
            return ResponseEntity.notFound().build();
    }
}
