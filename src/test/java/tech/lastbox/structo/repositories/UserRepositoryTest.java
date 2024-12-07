package tech.lastbox.structo.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import tech.lastbox.structo.model.UserEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setup(){
        userRepository.save(new UserEntity("nametest", "usernametest", "test@test.com", "Password@test"));
    }

    @Test
    public void encontroUsuarioPorEmail_receboSucesso(){
        Optional<UserEntity> user = userRepository.findUserByEmail("test@test.com");
        assertTrue(user.isPresent());
        assertEquals("nametest", user.get().getName());
    }

    @Test
    public void verificoSeUsuarioExistePorEmail_receboVerdadeiro(){
        assertTrue(userRepository.existsByEmail("test@test.com"));
    }

    @Test
    public void verificoSeUsuarioExistePorEmail_receboFalso(){
        assertFalse(userRepository.existsByEmail("naoexiste@test.com"));
    }

    @Test
    public void verificoSeUsuarioExistePorUsername_receboVerdadeiro(){
        assertTrue(userRepository.existsByUsername("usernametest"));
    }
}
