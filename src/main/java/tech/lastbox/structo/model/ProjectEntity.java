package tech.lastbox.structo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, length = 4096)
    private String description;

    @Column(nullable=false, length = 4096)
    private String tasks;

    @Column(nullable=false, length = 4096)
    private String fileStructure;

    @Column(nullable=false, length = 4096)
    private String diagram;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ChatHistory chatHistory;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable=false)
    private UserEntity user;

    public ProjectEntity() {}

    public ProjectEntity(long id, String name, String description, String tasks, String fileStructure, String diagram, ChatHistory chatHistory, UserEntity user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
        this.fileStructure = fileStructure;
        this.diagram = diagram;
        this.chatHistory = chatHistory;
        this.user = user;
    }

    public ProjectEntity(String name, String description, String tasks, String fileStructure, String diagram, UserEntity user) {
        this.name = name;
        this.description = description;
        this.tasks = tasks;
        this.fileStructure = fileStructure;
        this.diagram = diagram;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getFileStructure() {
        return fileStructure;
    }

    public void setFileStructure(String fileStructure) {
        this.fileStructure = fileStructure;
    }

    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public ProjectEntity setChatHistory(ChatHistory chatHistory) {
        this.chatHistory = chatHistory;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProjectEntity project)) return false;
        return id == project.id || Objects.equals(chatHistory, project.chatHistory) || Objects.equals(user, project.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatHistory, user);
    }
}
