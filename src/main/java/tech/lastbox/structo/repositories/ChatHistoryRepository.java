package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.lastbox.structo.model.ChatHistory;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
}
