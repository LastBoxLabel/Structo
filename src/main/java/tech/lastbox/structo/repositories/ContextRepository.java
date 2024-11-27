package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.lastbox.structo.model.ContextEntity;

public interface ContextRepository extends JpaRepository<ContextEntity, Long> {
}
