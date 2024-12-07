package tech.lastbox.structo.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.ProjectEntity;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.repositories.ProjectRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void dadosValidosParaCriacao_crioProjeto_receboProjetoCriado() {
        ChatHistory mockChatHistory = new ChatHistory();
        mockChatHistory.setId(10L);

        UserEntity mockUser = new UserEntity();

        ProjectEntity savedProject = new ProjectEntity();
        savedProject.setId(1L);
        savedProject.setName("Test Project");
        savedProject.setDescription("Description");
        savedProject.setUser(mockUser);
        savedProject.setChatHistory(mockChatHistory);

        when(chatService.createHistory("Test Project", "Description", "Tasks", "File Structure", "Diagram"))
                .thenReturn(mockChatHistory);
        when(projectRepository.save(any(ProjectEntity.class))).thenReturn(savedProject);

        ProjectEntity result = projectService.createProject(
                "Test Project",
                "Description",
                "Tasks",
                "File Structure",
                "Diagram",
                mockUser
        );

        assertNotNull(result);
        assertEquals("Test Project", result.getName());
        assertEquals("Description", result.getDescription());
        assertEquals(mockUser, result.getUser());
        assertEquals(mockChatHistory, result.getChatHistory());

        verify(chatService, times(1))
                .createHistory("Test Project", "Description", "Tasks", "File Structure", "Diagram");
        verify(projectRepository, times(2)).save(any(ProjectEntity.class));
        verify(chatService, times(1)).updateHistory(any(ChatHistory.class));
    }
}
