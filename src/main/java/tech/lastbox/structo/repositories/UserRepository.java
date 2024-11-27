package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.lastbox.lastshield.security.core.annotations.UserHandler;
import tech.lastbox.structo.model.UserEntity;

@UserHandler
public interface UserRepository extends JpaRepository<UserEntity, Long> {}
