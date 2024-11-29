package tech.lastbox.structo.services;

import org.springframework.stereotype.Service;
import tech.lastbox.structo.model.ProjectEntity;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.repositories.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectEntity createProject(String name, String description, String tasks, String fileStructure, String diagram, UserEntity user) {
        ProjectEntity project = new ProjectEntity(name, description, tasks, fileStructure, diagram, user);

        return projectRepository.save(project);
    }
}
