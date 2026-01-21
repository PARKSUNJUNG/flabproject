package space.chat.chatMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.chat.chatRoom.ChatRoom;
import space.chat.chatRoom.ChatRoomRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public void sendUserMessage(Long roomId, Long userId, String content){

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        // 이 방의 User가 맞는지 확인
        if(!room.getUser().getId().equals(userId)){
            throw new AccessDeniedException("메시지 전송 권한이 없습니다.");
        }

        ChatMessage message = new ChatMessage(
                room,
                SenderType.USER,
                userId,
                content
        );

        chatMessageRepository.save(message);

        room.updateLastMessageTime();
    }

    public void sendMemberMessage(Long roomId, Long memberId, String content){

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        if(!room.getMember().getId().equals(memberId)){
            throw new AccessDeniedException("메시지 전송 권한이 없습니다.");
        }

        ChatMessage message = new ChatMessage(
                room,
                SenderType.MEMBER,
                memberId,
                content
        );

        chatMessageRepository.save(message);
    }
}
