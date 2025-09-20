package project.snake.backend.core.factory;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import java.time.Clock;

@Factory
public class ClockFactory {

  @Singleton
  public Clock clock() {
    return Clock.systemUTC();
  }
}