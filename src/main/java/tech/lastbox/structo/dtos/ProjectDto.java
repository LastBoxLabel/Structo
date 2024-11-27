package tech.lastbox.structo.dtos;

import tech.lastbox.structo.model.DiagramsEntity;

import java.util.List;

public record ProjectDto(long id,
                         String name,
                         String description,
                         List<ContextDto> contextDtoList,
                         DiagramsEntity diagramsEntity) {}
