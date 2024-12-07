package tech.lastbox.structo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.lastbox.structo.model.types.Sender;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChatHistoryEntityTest {
    private ChatHistory chatHistory;
    private ProjectEntity project;
    private ChatMessage chatMessage;

    @BeforeEach
    void setup() {
        project = new ProjectEntity("nameTest",
                "descriptionTest",
                     "tasksTest",
                "fileStructureTest",
                "diagramTest",
                new UserEntity());
        chatMessage = new ChatMessage(Sender.USER, "messageTest", chatHistory);
        chatHistory = new ChatHistory("baseInfoTest");
    }

    @Test
    void testConstructor() {
        assertThat(chatHistory.getBaseInfo()).isEqualTo("baseInfoTest");
        assertThat(chatHistory.getChatMessages()).isNotNull();
    }

    @Test
    void testConstructorWithAllParams() {
        ChatHistory fullChatHistory = new ChatHistory(1L, List.of(chatMessage), project, "fullBaseInfoTest");
        assertThat(fullChatHistory.getBaseInfo()).isEqualTo("fullBaseInfoTest");
        assertThat(fullChatHistory.getChatMessages().getFirst()).isEqualTo(chatMessage);
        assertThat(fullChatHistory.getProject()).isEqualTo(project);
    }

    @Test
    void testAddChatMessages() {
        chatHistory.addChatMessages(chatMessage);
        assertThat(chatHistory.getChatMessages().contains(chatMessage)).isTrue();
    }

    @Test
    void testEquals() {
        ChatHistory anotherChatHistory = new ChatHistory("baseInfoTest");
        assertThat(chatHistory).isEqualTo(anotherChatHistory);
    }

    @Test
    void testHashCode() {
        ChatHistory anotherChatHistory = new ChatHistory("baseInfoTest");
        assertThat(chatHistory.hashCode()).isEqualTo(anotherChatHistory.hashCode());
    }

    @Test
    void testSettersAndGetters() {
        chatHistory.setBaseInfo("updateBaseInfoTest");
        chatHistory.setProject(project);
        assertThat(chatHistory.getBaseInfo()).isEqualTo("updateBaseInfoTest");
        assertThat(chatHistory.getProject()).isEqualTo(project);
    }
}
