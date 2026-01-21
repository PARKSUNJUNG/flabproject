package space.chat.chatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.member.Member;
import space.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<ChatRoom> findByUserAndMember(User user, Member member);

    List<ChatRoom> findByMemberId(Long memberId);
}
