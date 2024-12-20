package tech.lastbox.structo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lastbox.structo.exception.NotFoundException;
import tech.lastbox.structo.exception.user.AccessForbidden;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.ChatMessage;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.model.types.Sender;
import tech.lastbox.structo.repositories.ChatHistoryRepository;
import tech.lastbox.structo.repositories.ChatMessageRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        ChatMessage chatMessage = chatMessageRepository.save(new ChatMessage(sender, messageContent, chatHistory));
        chatHistoryRepository.save(chatHistory.addChatMessages(chatMessage));
        return chatMessage;
    }

    @Transactional
    public ChatHistory createUserMessage(Long historyId, String messageContent, UserEntity user) throws NotFoundException, AccessForbidden {
        UserEntity userEntity = chatHistoryRepository.findUserByHistoryId(historyId)
                                                     .orElseThrow(
                                                         () -> new NotFoundException("Histórico de chat não encontrado")
                                                     );

        if (!userEntity.equals(user)) throw new AccessForbidden("Usuário tentou acesso a um ChatHistory que não pertencia a ele.");

        ChatHistory chatHistory = getChatHistoryById(historyId);

        ChatMessage chatMessage = chatMessageRepository.save(new ChatMessage(Sender.USER, messageContent, chatHistory));
        chatHistoryRepository.save(chatHistory.addChatMessages(chatMessage));

        return chatHistory;
    }

    @Transactional
    public void updateHistory(ChatHistory chatHistory) {
        chatHistoryRepository.saveAndFlush(chatHistory);
    }

    public String getAllMessagesByChatHistoryId(Long historyId) {
        return chatMessageRepository.findChatMessagesByChatHistoryId(historyId).stream()
                .map(
                    message ->
                    message.getSender().value() +
                    " sent: " + message.getMessageContent()
                )
                .collect(Collectors.joining("\n"));
    }

    public ChatHistory getChatHistoryById(Long historyId) throws NotFoundException {
        return chatHistoryRepository.findChatHistoryById(historyId)
                .orElseThrow(() ->
                        new NotFoundException("Histórico de chat não encontrado")
                );
    }

    public List<ChatMessage> getUserChatHistoryByIdAsJson(Long historyId, UserEntity user) throws NotFoundException, AccessForbidden {
        UserEntity userEntity = chatHistoryRepository
                .findUserByHistoryId(historyId)
                .orElseThrow(
                        () -> new NotFoundException("Histórico de chat não encontrado")
                );

        if (!userEntity.equals(user)) throw new AccessForbidden("Usuário tentou acesso a um ChatHistory que não pertencia a ele.");

        return getMessagesByChatHistoryIdAsJson(historyId);

    }

    private List<ChatMessage> getMessagesByChatHistoryIdAsJson(Long historyId) {

        return chatMessageRepository.findChatMessagesByChatHistoryId(historyId);
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
