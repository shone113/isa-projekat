package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.*;
import rs.ac.uns.ftn.informatika.rest.dto.ChatDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IChatRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChatService {

    @Autowired
    private IChatRepository chatRepository;

    @Autowired
    private ProfileService profileService;
    @Autowired
    private ChatMemberService chatMemberService;

    public ChatService(){}

    @Transactional
    public ChatDTO findById(Integer id){
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat not found with ID: " + id));
        for (Profile profile : chat.getMemberProfiles()) {
            profile.setPosts(null);
        }
        return new ChatDTO(chat.getId(), chat.getTitle(), chat.getMemberProfiles(), chat.getAdminProfile().getId(), chat.getChatType());
    }

    @Transactional
    public List<ChatDTO> findAll(){
        List<Chat> chats = chatRepository.findAll();
        System.out.println("Broj pronađenih chatova: " + chats.size());
        List<ChatDTO> chatDTOs = new ArrayList<>();
        for(Chat chat : chats) {
            for (Profile profile : chat.getMemberProfiles()) {
                profile.setPosts(null);
            }
            ChatDTO chatDTO = new ChatDTO(chat);
            chatDTOs.add(chatDTO);
            System.out.println("Chat: " + chat.getTitle() + " " + chat.getId() + " " + chat.getChatType().equals(Chat.ChatType.GROUP));
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
            if (chat.getMemberProfiles().stream().anyMatch(profile -> profile.getId().equals(profileId))) {
                // Ako je trenutni korisnik član, postavljamo posts na null
                for (Profile profile : chat.getMemberProfiles()) {
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
    public ChatDTO getOrCreateDm(Integer firstProfileId, Integer secondProfileId){
        List<Chat> chats = chatRepository.getDmChats();

        if(chats.size() == 0){
            return createChatWithMembers(firstProfileId, secondProfileId);
        }
        for (Chat chat : chats) {
            Set<Integer> profileIds = new HashSet<>();
            // Pretpostavljamo da chat ima korisnike sa ID-evima
            for (Profile profile : chat.getMemberProfiles()) {
                profileIds.add(profile.getId());
                profile.setPosts(null);
            }

            // Proveravamo da li oba korisnika postoje u chat-u
            if (profileIds.contains(firstProfileId) && profileIds.contains(secondProfileId)) {
                return new ChatDTO(chat);
            }
        }
        return createChatWithMembers(firstProfileId, secondProfileId);
    }

    private ChatDTO createChatWithMembers(Integer firstProfileId, Integer secondProfileId){
        Chat chat = new Chat();
        chat.setChatType(Chat.ChatType.DM);
        chat.setAdminProfile(profileService.getProfileById(firstProfileId));
        Profile firstProfile = profileService.getProfileById(firstProfileId);
        Profile secondProfile = profileService.getProfileById(secondProfileId);
        firstProfile.setPosts(null);
        secondProfile.setPosts(null);
        ChatMember firstMember = new ChatMember(chat, firstProfile);
        ChatMember secondMember = new ChatMember(chat, secondProfile);
        ArrayList<ChatMember> members = new ArrayList<>();
        members.add(firstMember);
        members.add(secondMember);
        chat.setMembers(members);
        chatRepository.save(chat);
        return new ChatDTO(chat);
    }

    @Transactional
    public ChatDTO create(ChatDTO chatDTO){
        Profile adminProfile = profileService.getProfileById(chatDTO.getAdminProfileId());
        Chat chat = new Chat(chatDTO.getTitle(), chatDTO.getMembers(), adminProfile, chatDTO.getChatType());
        chatRepository.save(chat);
        List<Profile> profiles = new ArrayList<>();
        for(Profile profile : chatDTO.getMembers()) {
            profiles.add(profileService.getProfileById(profile.getId()));
            profile.setPosts(null);
        }
        return new ChatDTO(chat.getId(), chat.getTitle(), profiles, adminProfile.getId(), chat.getChatType());
    }

    @Transactional
    public ChatDTO addMember(Integer chatId, Integer profileId){
        Chat chat = chatRepository.getById(chatId);
//        chat.getMembers().add(new ChatMember(chat, profileService.getProfileById(profileId)));
        Integer chatMessagesCount = chatRepository.countMessagesForChat(chatId);
        chatMemberService.save(new ChatMember(chat, profileService.getProfileById(profileId), chatMessagesCount));
        for (Profile member : chat.getMemberProfiles()) {
            member.setPosts(null); // Postavljamo posts na null
        }
        chatRepository.save(chat);
        return new ChatDTO(chatId, chat.getTitle(), chat.getMemberProfiles(), chat.getAdminProfile().getId(), chat.getChatType());
    }

    @Transactional
    public ChatDTO removeMember(Integer chatId, Integer profileId){
        Chat chat = chatRepository.getById(chatId);
//        chat.getMembers().remove(profileService.getProfileById(profileId));
        chatMemberService.remove(new ChatMember(chat, profileService.getProfileById(profileId)));
        for (Profile member : chat.getMemberProfiles()) {
            member.setPosts(null); // Postavljamo posts na null
        }
        chatRepository.save(chat);
        return new ChatDTO(chatId, chat.getTitle(), chat.getMemberProfiles(), chat.getAdminProfile().getId(), chat.getChatType());
    }
}
