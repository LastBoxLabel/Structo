package tech.lastbox.structo.dtos;

import java.util.List;

public record UnityDto(String name,
                       String type,
                       List<String> requirements,
                       ContextDto context) {}
