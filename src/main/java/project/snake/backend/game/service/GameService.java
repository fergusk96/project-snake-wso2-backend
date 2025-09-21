package project.snake.backend.game.service;

import java.util.AbstractMap;
import java.util.List;
import project.snake.backend.game.domain.GameResult;
import project.snake.backend.game.entity.GameMongoEntity;

public interface GameService {
    void saveGame(GameResult gameResult);

    AbstractMap.SimpleEntry<List<GameResult>, List<GameResult>> retrieveGameHistory(String userId);
}
