package project.snake.backend.game.domain;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResult {
  final String playerOneId;
  final String playerTwoId;
  final String winnerId;
  final Instant gameFinishedAt;
}
