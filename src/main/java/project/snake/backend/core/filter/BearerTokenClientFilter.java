package project.snake.backend.core.filter;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import project.snake.backend.core.auth.outbound.service.AccessTokenService;

@Filter("/api/v1/**")
@Singleton
public class BearerTokenClientFilter implements HttpClientFilter {


  private final Provider<AccessTokenService> accessTokenServiceProvider;

  @Inject
  public BearerTokenClientFilter(final Provider<AccessTokenService> accessTokenServiceProvider) {
    this.accessTokenServiceProvider = accessTokenServiceProvider;
  }

  @Override
  public Publisher<? extends HttpResponse<?>> doFilter(final MutableHttpRequest<?> request,
                                                       final ClientFilterChain chain) {
    if (request.getPath().contains("/oauth2/token")) {
      return chain.proceed(request);
    }
    final String token = accessTokenServiceProvider.get().getAccessToken();
    request.header("Authorization", String.format("%s %s", "Bearer", token));
    return chain.proceed(request);
  }
}