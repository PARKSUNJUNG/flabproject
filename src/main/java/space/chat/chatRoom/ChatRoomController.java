package space.chat.chatRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.chat.chatMessage.ChatMessage;
import space.chat.chatMessage.ChatMessageRepository;
import space.user.Role;
import space.user.UserPrincipal;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @GetMapping("/room")
    public String enterChatRoom(
            @RequestParam Long memberId,
            @AuthenticationPrincipal UserPrincipal userDetails
    ){
        if (userDetails.getRole() != Role.USER) {
            throw new AccessDeniedException("USER만 접근 가능");
        }

        Long userId = userDetails.getId();
        ChatRoom room = chatRoomService.getOrCreateRoom(userId, memberId);

        return "redirect:/chat/room/"+room.getId();
    }

    @GetMapping("/room/{roomId}")
    public String chatRoom(
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserPrincipal userDetails,
            Model model
    ){
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        Long loginUserId = userDetails.getId();

        // 권한 체크 (이 방의 유저가 맞는지)
        if (!room.getUser().getId().equals(loginUserId)) {
            throw new AccessDeniedException("접근 권한이 없습니다."); // 403 Forbidden
        }

        List<ChatMessage> messages = chatMessageRepository.findByChatRoomOrderByCreatedAtAsc(room);

        model.addAttribute("room", room);
        model.addAttribute("member", room.getMember());
        model.addAttribute("messages", messages);

        return "user/chat/room";
    }
}
