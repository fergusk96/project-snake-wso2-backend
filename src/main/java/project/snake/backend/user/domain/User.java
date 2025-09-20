package project.snake.backend.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class User {
  private final String id;
  private final String providedId;
  private final String preferredEmail;
  private final String phone;
  private final String username;
  private final String lastName;
  private final String firstName;
  private final boolean isSuspended;
  private final String picture;
}