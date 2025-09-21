package project.snake.backend.game.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.snake.backend.game.domain.GameResult;
import project.snake.backend.game.entity.GameMongoEntity;
import project.snake.backend.game.response.GameResultResponse;

@Mapper(componentModel = "jakarta")
public interface GameMapper {

  GameResultResponse toGameResultResponse(GameResult gameResult);

  @Mapping(target = "id", ignore = true)
  GameMongoEntity toGameMongoEntity(GameResult gameResult);

  GameResult toGameResult(GameMongoEntity gameMongoEntity);
}