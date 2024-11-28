package tech.lastbox.structo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;

    @ElementCollection
    private List<String> tasks = new ArrayList<>();

    private String fileStructure;

    @Column(nullable = false)
    private String diagram;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private ChatHistory chatHistory;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    @JsonIgnore
    private UserEntity user;

    public ProjectEntity() {}

    public ProjectEntity(long id, String name, String description, List<String> tasks, String fileStructure, String diagram, ChatHistory chatHistory, UserEntity user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
        this.fileStructure = fileStructure;
        this.diagram = diagram;
        this.chatHistory = chatHistory;
        this.user = user;
    }

    public ProjectEntity(String name, String description, List<String> tasks, String fileStructure, String diagram) {
        this.name = name;
        this.description = description;
        this.tasks = tasks;
        this.fileStructure = fileStructure;
        this.diagram = diagram;
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

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
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

    public void setChatHistory(ChatHistory chatHistory) {
        this.chatHistory = chatHistory;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
