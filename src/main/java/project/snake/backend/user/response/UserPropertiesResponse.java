package project.snake.backend.user.response;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
@Serdeable
public class UserPropertiesResponse {
  private String code;
  private String message;
  private List<Property> properties;


  @Data
  @Builder
  @Getter
  @Serdeable
  public static class Property {
    private String id;
    private String key;
    private String name;
    private Object value;
    private String description;

  }
}