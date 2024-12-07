package tech.lastbox.structo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class UserEntityTest {

    private UserEntity user;

    @BeforeEach
    void setup() {
        user = new UserEntity("nameTest", "usernameTest", "test@test.com", "password123");
    }

    @Test
    void testConstructor() {
        assertThat(user.getName()).isEqualTo("nameTest");
        assertThat(user.getUsername()).isEqualTo("usernameTest");
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getRole()).isEqualTo("USER");
        assertThat(user.getProjects().isEmpty()).isTrue();
    }

    @Test
    void testAddProject() {
        ProjectEntity project = new ProjectEntity();
        user.addProject(project);

        assertThat(user.getProjects().size()).isEqualTo(1);
    }

    @Test
    void testSettersAndGetters() {
        user.setName("nameTest2");
        user.setUsername("usernameTest2");
        user.setEmail("test2@test.com");
        user.setPassword("newpassword123");
        user.setRole("ADMIN");

        assertThat(user.getName()).isEqualTo("nameTest2");
        assertThat(user.getUsername()).isEqualTo("usernameTest2");
        assertThat(user.getEmail()).isEqualTo("test2@test.com");
        assertThat(user.getPassword()).isEqualTo("newpassword123");
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }

    @Test
    void testEquality() {
        UserEntity user1 = new UserEntity("User", "equal", "equal@example.com", "password123");
        UserEntity user2 = new UserEntity("Duplicated User", "equal", "equal@example.com", "password456");

        assertThat(user1).isEqualTo(user2);
    }


    @Test
    void testDefaultRole() {
        assertThat(user.getRole()).isEqualTo("USER");
    }

}