package project.snake.backend.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import project.snake.backend.client.KindeClient;
import project.snake.backend.domain.User;
import project.snake.backend.mapper.KindeUserMapper;

@Singleton
@Slf4j
public class KindeUserService implements UserService {

  private final KindeClient kindeClient;
  private final KindeUserMapper kindeUserMapper;

  @Inject
  public KindeUserService(final KindeClient kindeClient, final KindeUserMapper kindeUserMapper) {
    this.kindeClient = kindeClient;
    this.kindeUserMapper = kindeUserMapper;
  }

  @Override
  public User getUserById(final String userId) {
    try {
      {
        return kindeClient.getUser(userId)
          .map(kindeUserMapper::toUser).get();
      }
    }
    catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }
}
