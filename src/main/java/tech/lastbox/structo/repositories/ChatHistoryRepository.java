package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    Optional<ChatHistory> findChatHistoryById(Long id);
    
    @Query("SELECT ch.project.user FROM ChatHistory ch WHERE ch.id = :historyId")
    Optional<UserEntity> findUserByHistoryId(@Param("historyId") Long historyId);

    @Query("SELECT ch FROM ChatHistory ch WHERE ch.project.user.id = :userId")
    List<ChatHistory> findChatHistoriesByUser(@Param("userId") Long userId);
}
