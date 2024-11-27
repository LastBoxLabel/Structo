package tech.lastbox.structo.dtos;

import java.util.List;

public record UserDto(String id,
                      String name,
                      String username,
                      String email,
                      List<ProjectDto> projectDtoList ){}