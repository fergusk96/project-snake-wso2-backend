package project.snake.backend.game.response;

import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Serdeable.Serializable
public class GameResultResponse {
  final String playerOneId;
  final String playerTwoId;
  final String winnerId;
  final Instant gameFinishedAt;
}