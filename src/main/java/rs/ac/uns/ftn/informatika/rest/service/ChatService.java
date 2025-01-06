package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Chat;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.dto.ChatDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IChatRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private IChatRepository chatRepository;

    @Autowired
    private ProfileService profileService;

    public ChatService(){}

    @Transactional
    public ChatDTO findById(Integer id){
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat not found with ID: " + id));
        for (Profile profile : chat.getMembers()) {
            profile.setPosts(null);
        }
        return new ChatDTO(chat.getId(), chat.getTitle(), chat.getMembers(), chat.getAdminProfile().getId());
    }

    @Transactional
    public List<ChatDTO> findAll(){
        List<Chat> chats = chatRepository.findAll();
        System.out.println("Broj pronađenih chatova: " + chats.size());
        List<ChatDTO> chatDTOs = new ArrayList<>();
        for(Chat chat : chats) {
            for (Profile profile : chat.getMembers()) {
                profile.setPosts(null);
            }
            ChatDTO chatDTO = new ChatDTO(chat);
            chatDTOs.add(chatDTO);
            System.out.println("Chat: " + chat.getTitle() + " " + chat.getId() + " " + chat.getMembers().size());
        }
        return chatDTOs;
    }

    @Transactional
    public List<ChatDTO> findAllForUser(Integer profileId){
        List<Chat> chats = chatRepository.findAll();
        System.out.println("Broj pronađenih chatova: " + chats.size());

        List<ChatDTO> chatDTOs = new ArrayList<>();

        // Filtriramo chatove u kojima je trenutni korisnik član
        for (Chat chat : chats) {
            // Prolazimo kroz članove chata da bismo našli trenutnog korisnika
            if (chat.getMembers().stream().anyMatch(profile -> profile.getId().equals(profileId))) {
                // Ako je trenutni korisnik član, postavljamo posts na null
                for (Profile profile : chat.getMembers()) {
                    profile.setPosts(null);
                }

                // Kreiramo DTO za taj chat
                ChatDTO chatDTO = new ChatDTO(chat);
                chatDTOs.add(chatDTO);
                System.out.println("Chat: " + chat.getTitle() + " " + chat.getId() + " " + chat.getMembers().size());
            }
        }

        return chatDTOs;
    }

    @Transactional
    public ChatDTO create(ChatDTO chatDTO){
        Profile adminProfile = profileService.getProfileById(chatDTO.getAdminProfileId());
        Chat chat = new Chat(chatDTO.getTitle(), chatDTO.getMembers(), adminProfile);
        chatRepository.save(chat);
        List<Profile> profiles = new ArrayList<>();
        for(Profile profile : chatDTO.getMembers()) {
            profiles.add(profileService.getProfileById(profile.getId()));
            profile.setPosts(null);
        }
        return new ChatDTO(chat.getId(), chat.getTitle(), profiles, adminProfile.getId());
    }

    @Transactional
    public ChatDTO addMember(Integer chatId, Integer profileId){
        Chat chat = chatRepository.getById(chatId);
        chat.getMembers().add(profileService.getProfileById(profileId));
        for (Profile member : chat.getMembers()) {
            member.setPosts(null); // Postavljamo posts na null
        }
        chatRepository.save(chat);
        return new ChatDTO(chatId, chat.getTitle(), chat.getMembers(), chat.getAdminProfile().getId());
    }

    @Transactional
    public ChatDTO removeMember(Integer chatId, Integer profileId){
        Chat chat = chatRepository.getById(chatId);
        chat.getMembers().remove(profileService.getProfileById(profileId));
        for (Profile member : chat.getMembers()) {
            member.setPosts(null); // Postavljamo posts na null
        }
        chatRepository.save(chat);
        return new ChatDTO(chatId, chat.getTitle(), chat.getMembers(), chat.getAdminProfile().getId());
    }
}
