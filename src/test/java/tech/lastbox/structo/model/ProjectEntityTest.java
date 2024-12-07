package tech.lastbox.structo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProjectEntityTest {

    private ProjectEntity projectEntity;
    private UserEntity user;
    private ChatHistory chatHistory;

    @BeforeEach
    public void setup() {
        user = new UserEntity("userTest", "userTest", "test@test.com", "password123");
        chatHistory = new ChatHistory();
        projectEntity = new ProjectEntity(1L, "nameTest",
                "descriptionTest",
                "tasksTest",
                "fileStructureTest",
                "diagramTest", chatHistory, user);
    }

    @Test
    void testConstructor() {
        assertThat(projectEntity.getName()).isEqualTo("nameTest");
        assertThat(projectEntity.getDescription()).isEqualTo("descriptionTest");
        assertThat(projectEntity.getTasks()).isEqualTo("tasksTest");
        assertThat(projectEntity.getFileStructure()).isEqualTo("fileStructureTest");
        assertThat(projectEntity.getDiagram()).isEqualTo("diagramTest");
        assertThat(projectEntity.getChatHistory()).isNotNull();
        assertThat(projectEntity.getUser()).isNotNull();
    }

    @Test
    void testSettersAndGetters() {
        projectEntity.setName("newName");
        projectEntity.setDescription("newDescription");
        projectEntity.setTasks("newTasks");
        projectEntity.setFileStructure("newFileStructure");
        projectEntity.setDiagram("newDiagram");

        assertThat(projectEntity.getName()).isEqualTo("newName");
        assertThat(projectEntity.getDescription()).isEqualTo("newDescription");
        assertThat(projectEntity.getTasks()).isEqualTo("newTasks");
        assertThat(projectEntity.getFileStructure()).isEqualTo("newFileStructure");
        assertThat(projectEntity.getDiagram()).isEqualTo("newDiagram");
    }

    @Test
    void testSetChatHistory() {
        ChatHistory newChatHistory = new ChatHistory();
        projectEntity.setChatHistory(newChatHistory);
        assertThat(projectEntity.getChatHistory()).isEqualTo(newChatHistory);
    }

    @Test
    void testSetUser() {
        UserEntity newUser = new UserEntity("new userTest", "newUserTest", "newTest@test.com", "password456");
        projectEntity.setUser(newUser);
        assertThat(projectEntity.getUser()).isEqualTo(newUser);
    }

    @Test
    void testAddProject() {
        UserEntity newUser = new UserEntity("user", "userTest", "test@test.com", "password789");
        ProjectEntity newProject = new ProjectEntity(1L, "newNameTest", "newDescriptionTest", "newTasksTest", "newFileStructureTest", "newDiagramTest", chatHistory, newUser);
        newUser.addProject(newProject);
        assertThat(newUser.getProjects().size()).isGreaterThan(0);
        assertThat(newUser.getProjects().getFirst()).isEqualTo(newProject);
    }
}