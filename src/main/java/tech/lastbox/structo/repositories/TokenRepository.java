package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.lastbox.jwt.TokenEntity;
import tech.lastbox.jwt.TokenStore;

public interface TokenRepository extends JpaRepository<TokenEntity, String>, TokenStore {
}
