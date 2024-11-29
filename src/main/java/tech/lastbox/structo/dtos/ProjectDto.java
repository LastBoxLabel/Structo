package tech.lastbox.structo.dtos;

public record ProjectDto(Long id, String name, String description, String tasks, String fileStructure, String diagram, ChatHistoryDto chatHistory) {
}
