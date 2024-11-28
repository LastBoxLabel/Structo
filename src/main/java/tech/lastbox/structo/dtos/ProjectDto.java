package tech.lastbox.structo.dtos;

import java.util.List;

public record ProjectDto(Long id, String name, String description, List<String> tasks, String fileStructure, String diagram, ChatHistoryDto chatHistory) {
}
