package project.snake.backend.game.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Serdeable.Serializable
@JsonInclude(JsonInclude.Include.ALWAYS)
public class GameHistoryResponse {
  final List<GameResultResponse> wins;
  final List<GameResultResponse> losses;
}