package tech.lastbox.structo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lastbox.structo.exception.NotFoundException;
import tech.lastbox.structo.exception.user.AccessForbidden;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.ChatMessage;
import tech.lastbox.structo.model.ProjectEntity;
import tech.lastbox.structo.model.UserEntity;
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

    @Transactional
    public ChatHistory createHistory(String name, String description, String tasks, String fileStructure, String diagram) {
        ChatHistory chatHistory = new ChatHistory(constructBaseInfo(name, description, tasks, fileStructure, diagram));

        return chatHistoryRepository.save(chatHistory);
    }

    @Transactional
    public ChatMessage addMessage(Sender sender, String messageContent, ChatHistory chatHistory) {
        ChatMessage chatMessage = chatMessageRepository.save(new ChatMessage(sender, messageContent));
        chatHistoryRepository.save(chatHistory.addChatMessages(chatMessage));
        return chatMessage;
    }

    @Transactional
    public ChatMessage createUserMessage(Long historyId, String messageContent, UserEntity user) throws NotFoundException, AccessForbidden {
        UserEntity userEntity = chatHistoryRepository.findUserByHistoryId(historyId)
                                                     .orElseThrow(
                                                         () -> new NotFoundException("Histórico de chat não encontrado")
                                                     );

        if (!userEntity.equals(user)) throw new AccessForbidden("Este chat não pode ser acessado pelo usuário");

        ChatHistory chatHistory = getChatHistoryById(historyId);

        ChatMessage chatMessage = chatMessageRepository.save(new ChatMessage(Sender.USER, messageContent));
        chatHistoryRepository.save(chatHistory.addChatMessages(chatMessage));

        return chatMessage;
    }

    public ChatHistory getChatHistoryById(Long historyId) throws NotFoundException {
        return chatHistoryRepository.findChatHistoryById(historyId)
                .orElseThrow(() ->
                        new NotFoundException("Histórico de chat não encontrado")
                );
    }

    private String constructBaseInfo(String name, String description, String tasks, String fileStructure, String diagram) {
        return String.format(
                "Nome do projeto: %s%n" +
                        "Descrição do projeto: %s%n" +
                        "Tasks do projeto: %s%n" +
                        "Estrutura do projeto: %s%n" +
                        "Diagrama do projeto: %s%n",
                name, description, tasks, fileStructure, diagram
        );
    }
}
