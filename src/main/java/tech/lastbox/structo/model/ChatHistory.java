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

    @Column(nullable = false, length = 10000)
    private String baseInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "project_id", unique = true)
    private ProjectEntity project;

    @OneToMany(mappedBy = "chatHistory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public ChatHistory() {}

    public ChatHistory(Long id, List<ChatMessage> chatMessages, ProjectEntity project, String baseInfo) {
        this.id = id;
        this.project = project;
        this.baseInfo = baseInfo;
        this.chatMessages = chatMessages;
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

    public ChatHistory addChatMessages(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChatHistory that)) return false;
        return Objects.equals(id, that.id) || Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project);
    }
}
