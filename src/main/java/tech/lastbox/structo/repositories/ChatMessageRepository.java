package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.lastbox.structo.model.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.chatHistory.id = :historyId ORDER BY cm.createdAt ASC")
    List<ChatMessage> findChatMessagesByChatHistoryId(@Param("historyId") long chatHistoryId);
}
