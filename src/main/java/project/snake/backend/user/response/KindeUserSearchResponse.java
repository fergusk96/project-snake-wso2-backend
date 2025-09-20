package project.snake.backend.user.response;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import project.snake.backend.user.dto.KindeUserDto;

@Introspected
@Serdeable
public record KindeUserSearchResponse(
  String code,
  String message,
  List<KindeUserDto> results
) {}