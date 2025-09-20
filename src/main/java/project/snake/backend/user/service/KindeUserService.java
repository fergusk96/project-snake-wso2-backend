package project.snake.backend.user.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import project.snake.backend.user.client.KindeClient;
import project.snake.backend.user.domain.User;
import project.snake.backend.core.mapper.UserMapper;

@Singleton
@Slf4j
public class KindeUserService implements UserService {

  private final KindeClient kindeClient;
  private final UserMapper userMapper;

  @Inject
  public KindeUserService(final KindeClient kindeClient, final UserMapper userMapper) {
    this.kindeClient = kindeClient;
    this.userMapper = userMapper;
  }

  @Override
  public Optional<User> getUserById(final String userId) {
    try {
      {
        return kindeClient.getUser(userId)
          .map(userMapper::toUser);
      }
    }
    catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }
}
