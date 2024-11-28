package tech.lastbox.structo.mappers;

import org.springframework.stereotype.Component;
import tech.lastbox.structo.dtos.ChatHistoryDto;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.ChatMessage;
import tech.lastbox.structo.repositories.ChatMessageRepository;

import java.util.List;

@Component
public class ChatHistoryMapper {

    private final ChatMessageRepository chatMessageRepository;

    public ChatHistoryMapper(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatHistoryDto toDto(ChatHistory chatHistory) {
        return new ChatHistoryDto(
                chatHistory.getId(),
                chatHistory.getBaseInfo(),
                chatHistory.getProject().getId(),
                getMessageHistory(chatHistory.getId())
        );
    }

    private List<ChatMessage> getMessageHistory(Long historyId) {
        return chatMessageRepository.findChatMessagesByChatHistoryId(historyId);
    }
}
