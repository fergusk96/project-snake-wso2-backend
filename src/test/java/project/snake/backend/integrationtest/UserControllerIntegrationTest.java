package project.snake.backend.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class UserControllerTest {

  @Inject
  @Client("/")
  HttpClient client;

  @Test
  void testGetUserByIdReturnsNotFound() {
    var request = HttpRequest.GET("/users/1");
    Assertions.assertThrows(Exception.class, () -> client.toBlocking().exchange(request));
  }
}
