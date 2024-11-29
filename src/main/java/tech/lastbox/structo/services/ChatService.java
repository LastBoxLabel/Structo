package tech.lastbox.structo.services;

import org.springframework.stereotype.Service;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.ChatMessage;
import tech.lastbox.structo.model.types.Sender;
import tech.lastbox.structo.repositories.ChatHistoryRepository;
import tech.lastbox.structo.repositories.ChatMessageRepository;

@Service
public class ChatService {
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatHistoryRepository chatHistoryRepository, ChatMessageRepository chatMessageRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatHistory createHistory(String baseInfo) {
        ChatHistory chatHistory = new ChatHistory(baseInfo);

        return chatHistoryRepository.save(chatHistory);
    }

    public ChatMessage addMessage(Sender sender, String messageContent, ChatHistory chatHistory) {
        ChatMessage chatMessage = chatMessageRepository.save(new ChatMessage(sender, messageContent));
        chatHistoryRepository.save(chatHistory.addChatMessages(chatMessage));
        return chatMessage;
    }
}
