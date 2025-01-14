package rs.ac.uns.ftn.informatika.rest.service;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Chat;
import rs.ac.uns.ftn.informatika.rest.domain.ChatMember;
import rs.ac.uns.ftn.informatika.rest.domain.Message;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.dto.ChatDTO;
import rs.ac.uns.ftn.informatika.rest.dto.MessageDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IMessageRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IProfileRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private IMessageRepository messageRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMemberService chatMemberService;

    public MessageService(){}

    @Transactional
    public List<MessageDTO> getAllForChat(Integer chatId){
        List<Message> messages = messageRepository.findAll();
        System.out.println("Ukupno pronađenih poruka: " + messages.size());
        List<Message> filteredMessages = messages.stream()
                .filter(message -> message.getChat().getId().equals(chatId)) // Filtriranje po chatId
                .sorted(Comparator.comparing(Message::getCreationDate)) // Sortiranje po datumu
                .collect(Collectors.toList());
        System.out.println("Poruke nakon filtriranja i sortiranja: " + filteredMessages.size());

        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : filteredMessages){
            MessageDTO messageDTO = new MessageDTO(message);
            messageDTO.setCreatorName(profileService.getProfileById(message.getCreator().getId()).getUser().getName());
            messageDTO.setCreatorSurname(profileService.getProfileById(message.getCreator().getId()).getUser().getSurname());
            messageDTOs.add(messageDTO);
        }
        return messageDTOs;
    }

    @Transactional
    public List<MessageDTO> getAllForUserProfile(Integer chatId, Integer profileId){
        ChatMember member = chatMemberService.getByProfileId(chatId, profileId);
        List<Message> messages = messageRepository.findMessagesForChat(chatId);
        Integer joinedAt = member.getJoiningMessage();
        if(joinedAt != null && joinedAt > 10 ){
            messages = messages.stream()
                    .limit(messages.size() - joinedAt + 10)
                    .collect(Collectors.toList());
            System.out.println("Limit na: " + (messages.size() - joinedAt + 10));
        }
//        messages = messageRepository.findAll();
        System.out.println("Ukupno pronađenih poruka: " + messages.size());
        List<Message> filteredMessages = messages.stream()
                .filter(message -> message.getChat().getId().equals(chatId)) // Filtriranje po chatId
                .sorted(Comparator.comparing(Message::getCreationDate)) // Sortiranje po datumu
                .collect(Collectors.toList());
        System.out.println("Poruke nakon filtriranja i sortiranja: " + filteredMessages.size());

        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : filteredMessages){
            MessageDTO messageDTO = new MessageDTO(message);
            messageDTO.setCreatorName(profileService.getProfileById(message.getCreator().getId()).getUser().getName());
            messageDTO.setCreatorSurname(profileService.getProfileById(message.getCreator().getId()).getUser().getSurname());
            messageDTOs.add(messageDTO);
        }
        return messageDTOs;
    }

    @Transactional
    public MessageDTO create(MessageDTO messageDTO){
        System.out.println("Ulazim u create-message");
        Profile profile = profileService.getProfileById(messageDTO.getCreatorId());
        ChatDTO chatDTO = chatService.findById(messageDTO.getChatId());
        Chat chat = new Chat(chatDTO);
        chat.setAdminProfile(profileService.getProfileById(chatDTO.getAdminProfileId()));
        Message message = new Message(messageDTO);
        message.setChat(chat);
        message.setCreator(profile);
        messageRepository.save(message);
        return messageDTO;
    }
}
