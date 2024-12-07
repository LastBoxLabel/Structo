package tech.lastbox.structo.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.lastbox.jwt.TokenEntity;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import java.time.Instant;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TokenRepositoryTest {

    @Autowired
    TokenRepository tokenRepository;

    @BeforeEach
    public void setup(){
        tokenRepository.save(new TokenEntity("tokenTest",
                                            Instant.now(),
                                            Instant.now(),
                                            "subjectTest",
                                            "issuerTest",
                                            List.of("scopeTest")));
    }

    @Test
    public void buscoTokenPorId_receboSucesso(){
        Optional<TokenEntity> token = tokenRepository.findById("tokenTest");
        assertTrue(token.isPresent());
        assertEquals("issuerTest", token.get().getIssuer());
    }

    @Test
    public void buscoTokenPorIdInvalido_receboSucesso(){
        Optional<TokenEntity> token = tokenRepository.findById("notValidTokenTest");
        assertFalse(token.isPresent());
    }
}
