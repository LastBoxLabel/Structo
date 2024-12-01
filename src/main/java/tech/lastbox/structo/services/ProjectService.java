package tech.lastbox.structo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.ProjectEntity;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.repositories.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ChatService chatService;

    public ProjectService(ProjectRepository projectRepository, ChatService chatService) {
        this.projectRepository = projectRepository;
        this.chatService = chatService;
    }

    @Transactional
    public ProjectEntity createProject(String name, String description, String tasks, String fileStructure, String diagram, UserEntity user) {
        ChatHistory chatHistory = chatService.createHistory(
                name,
                description,
                tasks,
                fileStructure,
                diagram
        );

        ProjectEntity project = projectRepository.save(new ProjectEntity(name, description, tasks, fileStructure, diagram, user));

        chatService.updateHistory(chatHistory.setProject(project));

        return projectRepository.save(project.setChatHistory(chatHistory));
    }

}
