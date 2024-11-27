package tech.lastbox.structo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import tech.lastbox.structo.model.types.Sender;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String messageContent;

    @Enumerated(EnumType.STRING)
    private Sender sender;

    private Instant createdAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "chat_history_id")
    @JsonIgnore
    private ChatHistory chatHistory;

    public ChatMessage() {}

    public ChatMessage(String messageContent, Sender sender) {
        this.messageContent = messageContent;
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ChatHistory chatHistory) {
        this.chatHistory = chatHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChatMessage that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
