package project.snake.backend.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.PropertyNamingStrategy;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;

@Introspected
@Serdeable.Deserializable(naming = SnakeCaseStrategy.class)
public record KindeUserDto(
  String id,
  String providedId,
  String preferredEmail,
  String phone,
  String username,
  String lastName,
  String firstName,
  boolean isSuspended,
  String picture
) {
}