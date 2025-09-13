package project.snake.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.snake.backend.domain.User;
import project.snake.backend.dto.KindeUserDto;

@Mapper(componentModel = "jakarta")
public interface KindeUserMapper {
  User toUser(KindeUserDto dto);
}