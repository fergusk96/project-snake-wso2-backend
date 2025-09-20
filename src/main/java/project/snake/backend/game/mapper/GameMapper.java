package project.snake.backend.game.mapper;

import org.mapstruct.Mapper;
import project.snake.backend.game.domain.GameResult;
import project.snake.backend.game.response.GameResultResponse;

@Mapper(componentModel = "jakarta")
public interface GameMapper {

  GameResultResponse toGameResultResponse(GameResult gameResult);
}