package project.snake.backend.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import java.util.Optional;
import lombok.NonNull;
import project.snake.backend.dto.KindeUserDto;

@Client(value = "user-service-client")
public interface KindeClient {

  @Get("/api/v1/user")
  Optional<KindeUserDto> getUser(@NonNull @QueryValue String id);
}
