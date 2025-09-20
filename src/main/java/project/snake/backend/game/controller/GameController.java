package project.snake.backend.game.controller;

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
import project.snake.backend.game.mapper.GameMapper;
import project.snake.backend.game.service.GameExecutionService;
import project.snake.backend.user.service.UserService;

@Singleton
@Getter(PROTECTED)
@Setter(PROTECTED)
@Controller("/game")
@ExecuteOn(TaskExecutors.BLOCKING)
public class GameController {

  private final UserService userService;
  private final GameExecutionService gameExecutionService;
  private final GameMapper gameMapper;

  @Inject
  public GameController(UserService userService, final GameExecutionService gameExecutionService,
                        final GameMapper gameMapper) {
    this.userService = userService;
    this.gameExecutionService = gameExecutionService;
    this.gameMapper = gameMapper;
  }

  @Operation(summary = "Get user by ID", description = "Returns a user for the given ID")
  @Get("/")
  public HttpResponse<?> executeGame(@RequestAttribute("userId") String userId) {
    var gameResult = gameMapper.toGameResultResponse(gameExecutionService.executeGame(userId));
    return HttpResponse.ok(gameResult);
  }
}