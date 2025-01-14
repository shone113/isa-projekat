package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Chat;
import rs.ac.uns.ftn.informatika.rest.domain.ChatMember;
import rs.ac.uns.ftn.informatika.rest.repository.IChatMemberRepository;

import java.util.List;

@Service
public class ChatMemberService {
    @Autowired
    private IChatMemberRepository chatMemberRepository;

    public ChatMemberService(){}

    public void save(ChatMember chatMember){
        chatMemberRepository.save(chatMember);
    }

    public void remove(ChatMember chatMember){
        chatMemberRepository.delete(chatMember);
    }

    public ChatMember getByProfileId(Integer chatId, Integer profileId){
        return chatMemberRepository.getByChatIdAndProfileId(chatId, profileId);

    }
}
