package tech.lastbox.structo.mappers;

import org.springframework.stereotype.Component;
import tech.lastbox.structo.dtos.ProjectDto;
import tech.lastbox.structo.model.ProjectEntity;

@Component
public class ProjectMapper {

    private final ChatHistoryMapper chatHistoryMapper;

    public ProjectMapper(ChatHistoryMapper chatHistoryMapper) {
        this.chatHistoryMapper = chatHistoryMapper;
    }

    public ProjectDto toDto(ProjectEntity project) {
        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getTasks(),
                project.getFileStructure(),
                project.getDiagram(),
                chatHistoryMapper.toDto(project.getChatHistory())
        );
    }
}
