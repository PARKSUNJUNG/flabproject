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
@RequestMapping("/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping("/message")
    public String sendMessage(
            @RequestParam Long roomId,
            @RequestParam String content,
            @AuthenticationPrincipal UserPrincipal userDetails
    ){
        Long userId = userDetails.getId();

        chatMessageService.sendUserMessage(roomId, userId, content);

        return "redirect:/chat/room/"+roomId;
    }
}
