package project.snake.backend.user.domain;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
  private Map<String, Object> addtionalProperties;
}