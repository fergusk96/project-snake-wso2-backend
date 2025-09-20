package project.snake.backend.user.controller;

import static io.micronaut.http.HttpResponse.notFound;
import static lombok.AccessLevel.PROTECTED;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.RequestAttribute;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import project.snake.backend.core.mapper.UserMapper;
import project.snake.backend.user.service.UserService;

@Singleton
@Getter(PROTECTED)
@Setter(PROTECTED)
@Controller("/users")
@ExecuteOn(TaskExecutors.BLOCKING)
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @Inject
  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @Operation(summary = "Get user by ID", description = "Returns a user for the given ID")
  @Get("/")
  public HttpResponse<?> getUserById(@RequestAttribute("userId") String userId) {
    return userService.getUserById(userId).map(userMapper::toUserResponse)
      .map(HttpResponse::ok)
      .orElse(notFound());
  }
}