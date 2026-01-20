package space.chat.chatRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import space.chat.chatMessage.ChatMessage;
import space.chat.chatMessage.ChatMessageRepository;
import space.user.Role;
import space.user.UserPrincipal;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/chat")
public class MemberChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @GetMapping("/room/{roomId}")
    public String chatRoom(
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserPrincipal memberDetails,
            Model model
    ){
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        if (memberDetails.getRole() != Role.MEMBER) {
            throw new AccessDeniedException("MEMBER만 접근 가능");
        }

        Long loginMemberId = memberDetails.getId();

        // 이 방의 연예인이 맞는지
        if(!room.getMember().getId().equals(loginMemberId)){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        List<ChatMessage> messages =
                chatMessageRepository.findByChatRoomOrderByCreatedAtAsc(room);

        model.addAttribute("room", room);
        model.addAttribute("user", room.getUser());
        model.addAttribute("messages", messages);

        return "/user/chat/memberRoom";
    }
}
