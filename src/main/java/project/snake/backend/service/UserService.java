package project.snake.backend.service;

import project.snake.backend.domain.User;

public interface UserService {

  User getUserById(String userId);
}
