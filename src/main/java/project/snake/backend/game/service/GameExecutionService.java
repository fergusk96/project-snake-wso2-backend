package project.snake.backend.game.service;

import project.snake.backend.game.domain.GameResult;

public interface GameExecutionService {
    GameResult executeGame(String userId);
}
