package project.snake.backend.controller;

import static lombok.AccessLevel.PROTECTED;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.RequestAttribute;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.snake.backend.domain.User;
import project.snake.backend.service.UserService;

@Singleton
@Getter(PROTECTED)
@Setter(PROTECTED)
@Controller("/users")
@ExecuteOn(TaskExecutors.BLOCKING)
public class UserController {

  private final UserService userService;

  @Inject
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Get user by ID", description = "Returns a user for the given ID")
  @Get("/")
  public HttpResponse<?> getUserById(@RequestAttribute("userId") String userId) {
    final User user = userService.getUserById(userId);
    return HttpResponse.notFound();
  }
}