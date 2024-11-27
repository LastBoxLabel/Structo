package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.lastbox.structo.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {}
