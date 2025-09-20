package project.snake.backend.user.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import java.util.Optional;
import lombok.NonNull;
import project.snake.backend.user.dto.KindeUserDto;
import project.snake.backend.user.requests.UpdateUserPropertiesRequest;
import project.snake.backend.user.response.KindeUserSearchResponse;
import project.snake.backend.user.response.UserPropertiesResponse;

@Client(value = "user-service-client")
public interface KindeClient {

  @Get("/api/v1/user")
  Optional<KindeUserDto> getUser(@NonNull @QueryValue String id);

  @Get("/api/v1/users/{id}/properties")
  Optional<UserPropertiesResponse> getUserProperties(@NonNull @PathVariable String id);

  @Get("/api/v1/search/users")
  KindeUserSearchResponse searchUsers(@Nullable @QueryValue("properties[user_rank]") Integer rank,
                                      @QueryValue("page_size") Integer pageSize);

  @Patch("/api/v1/users/{id}/properties")
  Object patchUser(@NonNull @PathVariable String id, @NonNull @Body UpdateUserPropertiesRequest updateUser);
}
