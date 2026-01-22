package space.chat.chatMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.chat.chatRoom.ChatRoom;
import space.chat.chatRoom.ChatRoomRepository;
import space.chat.websocket.ChatMessageDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageDto sendUserMessage(Long roomId, Long userId, String content){

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

        return new ChatMessageDto(
                message.getId(),
                room.getId(),
                message.getSenderType(),
                message.getSenderId(),
                message.getContent(),
                message.getCreatedAt()
        );
    }

    public ChatMessageDto sendMemberMessage(Long roomId, Long memberId, String content){

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

        return new ChatMessageDto(
                message.getId(),
                room.getId(),
                message.getSenderType(),
                message.getSenderId(),
                message.getContent(),
                message.getCreatedAt()
        );
    }


    public void broadcast(Long memberId, String content) {

        // 1. 이 연예인과 채팅한 모든 방 조회
        List<ChatRoom> rooms = chatRoomRepository.findByMemberId(memberId);

        // 2. 각 방에 메시지 저장
        for(ChatRoom room : rooms){
            ChatMessage message = new ChatMessage(
                    room,
                    SenderType.MEMBER,
                    memberId,
                    content
            );

            chatMessageRepository.save(message);

            room.updateLastMessageTime();
        }
    }
}
