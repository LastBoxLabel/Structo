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

    @ElementCollection
    private List<String> fileStructure = new ArrayList<>();

    @Column(nullable = false)
    private String diagram;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private ChatHistory chatHistory;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    @JsonIgnore
    private UserEntity user;

    public ProjectEntity() {}

    public ProjectEntity(long id, String name, String description, List<String> tasks, List<String> fileStructure, String diagram, ChatHistory chatHistory, UserEntity user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
        this.fileStructure = fileStructure;
        this.diagram = diagram;
        this.chatHistory = chatHistory;
        this.user = user;
    }

    public ProjectEntity(String name, String description, List<String> tasks, List<String> fileStructure, String diagram) {
        this.name = name;
        this.description = description;
        this.tasks = tasks;
        this.fileStructure = fileStructure;
        this.diagram = diagram;
    }
}
