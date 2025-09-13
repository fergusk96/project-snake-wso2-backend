package project.snake.backend.auth.inbound.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class JwtVerificationExceptionHandler implements ExceptionHandler<JWTVerificationException, HttpResponse<?>> {

  @Override
  public HttpResponse<?> handle(io.micronaut.http.HttpRequest request, JWTVerificationException exception) {
    return HttpResponse.unauthorized()
      .body(new JsonError("Invalid or expired JWT: " + exception.getMessage()));
  }
}