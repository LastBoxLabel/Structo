package tech.lastbox.structo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.lastbox.structo.model.types.Sender;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChatMessageEntityTest {

    private ChatMessage chatMessage;
    private ChatHistory chatHistory;
    private Sender sender;

    @BeforeEach
    void setup() {
        chatHistory = new ChatHistory();
        sender = Sender.USER;
        chatMessage = new ChatMessage(sender, "messageTest", chatHistory);
    }

    @Test
    void testConstructor() {
        assertThat(chatMessage.getMessageContent()).isEqualTo("messageTest");
        assertThat(chatMessage.getSender()).isEqualTo(sender);
        assertThat(chatMessage.getChatHistory()).isEqualTo(chatHistory);
        assertThat(chatMessage.getCreatedAt()).isNotNull();
    }

    @Test
    void testEquals() {
        ChatMessage anotherMessage = new ChatMessage(sender, "messageTest", chatHistory);
        anotherMessage.setId(chatMessage.getId());
        assertThat(chatMessage).isEqualTo(anotherMessage);
    }

    @Test
    void testNotEquals() {
        ChatMessage anotherMessage = new ChatMessage(sender, "messageTest", chatHistory);
        anotherMessage.setId(2L);
        assertThat(chatMessage).isNotEqualTo(anotherMessage);
    }

    @Test
    void testSettersAndGetters() {
        chatMessage.setMessageContent("updatedMessageTest");
        chatMessage.setSender(Sender.MODEL);
        chatMessage.setCreatedAt(Instant.now().minusSeconds(3600));

        assertThat(chatMessage.getMessageContent()).isEqualTo("updatedMessageTest");
        assertThat(chatMessage.getSender()).isEqualTo(Sender.MODEL);
        assertThat(chatMessage.getCreatedAt()).isBefore(Instant.now());
    }
}