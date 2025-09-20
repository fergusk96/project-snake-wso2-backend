package project.snake.backend.game.service;

import jakarta.inject.Singleton;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import project.snake.backend.game.domain.GameResult;
import project.snake.backend.game.exception.GameException;
import project.snake.backend.user.domain.User;
import project.snake.backend.user.requests.UpdateUserPropertiesRequest;
import project.snake.backend.user.service.UserService;


@Singleton
@Slf4j
public class GameExecutionServiceImplementation implements GameExecutionService {

  private final Clock clock;
  private final UserService userService;

  public GameExecutionServiceImplementation(final Clock clock, final UserService userService) {
    this.clock = clock;
    this.userService = userService;
  }

  @Override
  public GameResult executeGame(String userId) {
    final User userOne = userService.getUserById(userId, List.of("user_rank")).get();
    final int userOneRank = Integer.parseInt((String) userOne
      .getAddtionalProperties().getOrDefault("user_rank", "0"));
    final User userTwo = findCompatibleUser(userOneRank, userOne.getId());
    final Instant endTime = clock.instant();
    final User winner = endTime.getEpochSecond() % 2 == 0 ? userOne : userTwo;
    final int newRank = winner.equals(userOne) ? userOneRank + 1 : userOneRank - 1;
    userService.updateUser(userOne.getId(), UpdateUserPropertiesRequest.builder().properties(
      UpdateUserPropertiesRequest.UpdateUserRequestDto.builder().userRank(Math.max(0, newRank)).build()).build());
    final GameResult gameResult = GameResult.builder().winnerId(winner.getId())
      .gameFinishedAt(endTime)
      .playerOneId(userOne.getId())
      .playerTwoId(userTwo.getId())
      .build();
    // TODO Persist game result if needed
    log.info("Game executed: {}", gameResult);
    return gameResult;
  }

  private User findCompatibleUser(int rankToCheck, String userOneId) {
    List<Integer> ranksToCheck = List.of(rankToCheck);

    for (int i = 0; i <= 5; i++) {
      for(int rank : ranksToCheck) {
        if(rank < 0) continue;
        final List<User> users = userService.findUserByRank(rank);
        final Optional<User> compatibleUser = users.stream()
          .filter(user -> !user.getId().equals(userOneId))
          .findAny();
        if (compatibleUser.isPresent()) {
          return compatibleUser.get();
        }
      }
      ranksToCheck = List.of(rankToCheck - i, rankToCheck + i);
    }
    throw new GameException("No compatible user found");
  }
}
