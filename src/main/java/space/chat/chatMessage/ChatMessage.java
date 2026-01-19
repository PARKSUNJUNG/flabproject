package space.chat.chatMessage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.chat.chatRoom.ChatRoom;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor
public class ChatMessage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender_type", nullable = false)
    private SenderType senderType;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ChatMessage(ChatRoom chatRoom, SenderType senderType, Long senderId, String content) {
        this.chatRoom = chatRoom;
        this.senderType = senderType;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
