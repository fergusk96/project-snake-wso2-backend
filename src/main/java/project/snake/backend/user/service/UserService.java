package project.snake.backend.user.service;

import java.util.Optional;
import project.snake.backend.user.domain.User;

public interface UserService {

  Optional<User> getUserById(String userId);
}
