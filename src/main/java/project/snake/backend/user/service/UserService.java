package project.snake.backend.user.service;

import java.util.List;
import java.util.Optional;
import project.snake.backend.user.domain.User;
import project.snake.backend.user.requests.UpdateUserPropertiesRequest;

public interface UserService {

  Optional<User> getUserById(String userId, List<String> withProperties);
  List<User> findUserByRank(int rank);
  void updateUser(String id, UpdateUserPropertiesRequest command);
}
