package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Message;
import rs.ac.uns.ftn.informatika.rest.repository.MQRepository;

import java.util.List;

@Service
public class MQService {
    @Autowired
    private MQRepository mqRepository;

    public Message add(Message message) {
        return mqRepository.save(message);
    }

    public List<Message> getAllByQueue(String queue) {
        try{
            return mqRepository.findByQueue(queue);
        }
        catch(Exception e){
            return null;
        }
    }

    public void remove(Integer id){
        mqRepository.deleteById(id);
    }
}
