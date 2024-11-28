package tech.lastbox.structo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import tech.lastbox.structo.model.types.Sender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "chat_histories")
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String baseInfo;

    @OneToOne
    @JoinColumn(name = "project_id", unique = true)
    @JsonIgnore
    private ProjectEntity project;

    @OneToMany(mappedBy = "chatHistory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public ChatHistory() {}

    public ChatHistory(Long id, List<ChatMessage> chatMessages, ProjectEntity project, String baseInfo) {
        this.chatMessages = chatMessages;
        this.project = project;
        this.baseInfo = baseInfo;
        this.id = id;
    }

    public ChatHistory(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public boolean addChatMessages(String message, Sender sender) {
        return chatMessages.add(new ChatMessage(message, sender));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChatHistory that)) return false;
        return Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(project);
    }
}
