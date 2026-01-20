package space.chat.chatMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.user.UserPrincipal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/chat")
public class MemberChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping("/message")
    public String sendMessage(
            @RequestParam Long roomId,
            @RequestParam String content,
            @AuthenticationPrincipal UserPrincipal memberDetails
    ){
        Long memberId = memberDetails.getId();

        chatMessageService.sendMemberMessage(roomId, memberId, content);

        return "redirect:/member/chat/room/"+roomId;
    }
}
