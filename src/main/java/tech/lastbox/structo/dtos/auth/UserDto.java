package tech.lastbox.structo.dtos.auth;

import tech.lastbox.structo.model.ProjectEntity;

import java.util.List;

public record UserDto(Long id,
                      String name,
                      String username,
                      String email,
                      List<ProjectEntity> projectEntities){}