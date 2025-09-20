package project.snake.backend.user.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import project.snake.backend.user.client.KindeClient;
import project.snake.backend.user.domain.User;
import project.snake.backend.user.mapper.UserMapper;
import project.snake.backend.user.requests.UpdateUserPropertiesRequest;

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
  public Optional<User> getUserById(final String userId, final List<String> withProperties) {
    try {
        Optional<User> user = kindeClient.getUser(userId)
          .map(userMapper::toUser);

        if (user.isPresent() && !withProperties.isEmpty()) {
          Map<String, Object> props = new HashMap<>();
          kindeClient.getUserProperties(userId).ifPresent(propertiesResponse -> {
            propertiesResponse.getProperties().forEach(property -> {
              if(withProperties.contains(property.getKey())) {
                props.put(property.getKey(), property.getValue());
              }
            });
          });
            user.get().setAddtionalProperties(props);
        }
        return user;
    }
    catch (Exception e) {
      log.error(e.getMessage());
      return Optional.empty();
    }
  }

  @Override
  public List<User> findUserByRank(final int rank) {
    var response = kindeClient.searchUsers(rank, 10);
    if (response == null || response.results() == null) return List.of();
    return response.results().stream()
      .map(userMapper::toUser)
      .toList();
  }

  @Override
  public void updateUser(final String id, final UpdateUserPropertiesRequest request) {
      kindeClient.patchUser(id, request);
    }
  }

