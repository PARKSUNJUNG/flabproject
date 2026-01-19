package space.chat.chatRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.member.Member;
import space.member.MemberRepository;
import space.user.User;
import space.user.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    public ChatRoom getOrCreateRoom (Long userId, Long memberId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found."));

        return chatRoomRepository
                .findByUserAndMember(user, member)
                .orElseGet(()-> chatRoomRepository.save(new ChatRoom(user, member)));
    }
}
