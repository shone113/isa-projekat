package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.rest.domain.ChatMember;

public interface IChatMemberRepository extends JpaRepository<ChatMember, Integer> {

    @Query("SELECT cm FROM ChatMember cm WHERE cm.chat.id = :chatId AND cm.profile.id = :profileId")
    ChatMember getByChatIdAndProfileId(@Param("chatId") Integer chatId, @Param("profileId") Integer profileId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ChatMember cm WHERE cm.chat.id = :chatId AND cm.profile.id = :profileId")
    int deleteChatMember(@Param("chatId") Integer chatId, @Param("profileId") Integer profileId);

}
