package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.lastbox.structo.model.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {}
