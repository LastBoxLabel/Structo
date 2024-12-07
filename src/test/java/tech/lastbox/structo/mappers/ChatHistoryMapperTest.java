package tech.lastbox.structo.mappers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.lastbox.structo.dtos.ChatHistoryDto;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.ChatMessage;
import tech.lastbox.structo.model.ProjectEntity;
import tech.lastbox.structo.repositories.ChatMessageRepository;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ChatHistoryMapperTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    private ChatHistory chatHistory;

    @InjectMocks
    private ChatHistoryMapper chatHistoryMapper;

    @BeforeEach
    void setup() {
        ProjectEntity project = new ProjectEntity();
        project.setId(1L);
        chatHistory = new ChatHistory();
        chatHistory.setId(1L);
        chatHistory.setBaseInfo("Test info");
        chatHistory.setProject(project);

        when(chatMessageRepository.findChatMessagesByChatHistoryId(chatHistory.getId())).thenReturn(List.of(new ChatMessage(), new ChatMessage(), new ChatMessage()));
    }

    @Test
    void dadoChatHistoryValido_convertoEmDto_receboComoEntidade() {
        ChatHistoryDto result = chatHistoryMapper.toDto(chatHistory);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test info", result.baseInfo());
        assertEquals(1L, result.projectId());
        assertEquals(3, result.chatMessages().size());
    }
}
