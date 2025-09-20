package project.snake.backend.user.response;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Serdeable.Serializable
public class UserResponse {
  private final String email;
  private final String phone;
  private final String lastName;
  private final String firstName;
  private final String picture;
}