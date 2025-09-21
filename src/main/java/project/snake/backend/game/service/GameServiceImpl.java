package project.snake.backend.game.service;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import jakarta.inject.Singleton;
import java.util.AbstractMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import project.snake.backend.game.domain.GameResult;
import project.snake.backend.game.entity.GameMongoEntity;
import project.snake.backend.game.mapper.GameMapper;
import project.snake.backend.game.repository.GameMongoRepository;

@Singleton
@Slf4j
public class GameServiceImpl implements GameService {

  private final GameMongoRepository gameMongoRepository;
  private final GameMapper gameMapper;
  private final MongoClient mongoClient;

  public GameServiceImpl(final GameMongoRepository gameMongoRepository, final GameMapper gameMapper, final MongoClient mongoClient) {
    this.gameMongoRepository = gameMongoRepository;
    this.gameMapper = gameMapper;
    this.mongoClient = mongoClient;
  }

  @Override
  public void saveGame(final GameResult gameResult) {
    try (ClientSession session = mongoClient.startSession()) {
      session.withTransaction(() -> {
        gameMongoRepository.save(gameMapper.toGameMongoEntity(gameResult));
        return "Game saved";
      });
    }
  }

  @Override
  public AbstractMap.SimpleEntry<List<GameResult>, List<GameResult>> retrieveGameHistory(final String userId) {
    List<GameMongoEntity> allGames = gameMongoRepository.findByPlayerOneId(userId);

    List<GameResult> wins = allGames.stream()
      .filter(game -> userId.equals(game.getWinnerId()))
      .map(gameMongoEntity -> gameMapper.toGameResult(gameMongoEntity))
      .toList();

    List<GameResult> losses = allGames.stream()
      .filter(game -> !userId.equals(game.getWinnerId()))
      .map(gameMongoEntity -> gameMapper.toGameResult(gameMongoEntity))
      .toList();

    return new AbstractMap.SimpleEntry<>(wins, losses);
  }
}
