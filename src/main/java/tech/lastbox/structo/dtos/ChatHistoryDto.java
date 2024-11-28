package tech.lastbox.structo.dtos;

import tech.lastbox.structo.model.ChatMessage;

import java.util.List;

public record ChatHistoryDto(Long id, String baseInfo, Long projectId, List<ChatMessage> chatMessages) {
}
/*
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
    @JsonIgnore
    private List<ChatMessage> chatMessages = new ArrayList<>();
 */