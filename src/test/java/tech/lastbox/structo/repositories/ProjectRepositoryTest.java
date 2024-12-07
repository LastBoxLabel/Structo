package tech.lastbox.structo.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.ChatMessage;
import tech.lastbox.structo.model.ProjectEntity;
import tech.lastbox.structo.model.UserEntity;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @BeforeEach
    public void setup(){
        projectRepository.save(new ProjectEntity(1L, "nameTest", 
                "descriptionTest",
                "tasksTest",
                "fileStructureTest",
                "diagramTest",
                new ChatHistory(),
                new UserEntity()));
    }


}
