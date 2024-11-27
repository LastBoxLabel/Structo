package tech.lastbox.structo.dtos;

import tech.lastbox.structo.model.ProjectEntity;

import java.util.List;

public record UserDto(String id,
                      String name,
                      String username,
                      String email,
                      List<ProjectEntity> projectEntities){}