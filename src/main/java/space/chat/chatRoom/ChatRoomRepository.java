package space.chat.chatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import space.member.Member;
import space.user.User;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByUserAndMember(User user, Member member);
}
