package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.UserEntity;

import java.util.Optional;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    @Query("SELECT ch.project.user FROM ChatHistory ch WHERE ch.id = :historyId")
    Optional<UserEntity> findUserByHistoryId(@Param("historyId") Long id);
}
