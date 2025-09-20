package project.snake.backend.user.requests;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Builder;

@Builder
@Serdeable
public record UpdateUserPropertiesRequest(
  UpdateUserRequestDto properties) {

  @Builder
  @Serdeable(naming = SnakeCaseStrategy.class)
  public record UpdateUserRequestDto(Integer userRank) {
  }
}