package project.snake.backend.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.snake.backend.user.domain.User;
import project.snake.backend.user.dto.KindeUserDto;
import project.snake.backend.user.response.UserResponse;

@Mapper(componentModel = "jakarta")
public interface UserMapper {
  User toUser(KindeUserDto dto);

  @Mapping(source = "preferredEmail", target = "email")
  UserResponse toUserResponse(User user);
}