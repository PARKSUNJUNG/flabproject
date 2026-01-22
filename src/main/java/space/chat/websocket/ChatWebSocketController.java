package space.chat.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import space.chat.chatMessage.ChatMessageService;
import space.chat.chatMessage.SenderType;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessageDto dto){

        ChatMessageDto response;

        if(dto.getSenderType() == SenderType.USER){
            response = chatMessageService.sendUserMessage(
                    dto.getRoomId(),
                    dto.getSenderId(),
                    dto.getContent()
            );
        } else {
            response = chatMessageService.sendMemberMessage(
                    dto.getRoomId(),
                    dto.getSenderId(),
                    dto.getContent()
            );
        }

        // 채팅방 구독자에게 실시간 전송
        simpMessagingTemplate.convertAndSend(
                "/topic/chat/" + dto.getRoomId(),
                response
        );
    }
}
