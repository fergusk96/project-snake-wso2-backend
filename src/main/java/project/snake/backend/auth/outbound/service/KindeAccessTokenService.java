package project.snake.backend.auth.outbound.service;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Singleton;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class KindeAccessTokenService implements AccessTokenService {

  @Value("${kinde.client-url}")
  private String clientUrl;

  @Value("${kinde.client-id}")
  private String clientId;

  @Value("${kinde.client-secret}")
  private String clientSecret;

  @Value("${kinde.audience}")
  private String audience;

  private final OAuthClient oAuthClient;

  public KindeAccessTokenService(OAuthClient oAuthClient) {
    this.oAuthClient = oAuthClient;
  }

  public String getAccessToken() {
    String body = Map.of(
        "grant_type", "client_credentials",
        "client_id", clientId,
        "client_secret", clientSecret,
        "audience", String.format("%s/%s", clientUrl, audience)
      ).entrySet().stream()
      .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
      .collect(Collectors.joining("&"));
    Map<String, Object> response = oAuthClient.getToken(body);
    return (String) response.get("access_token");
  }

  @Client("oauth-client")
  interface OAuthClient {
    @Post("/oauth2/token")
    @Header(name = "Content-Type", value = "application/x-www-form-urlencoded")
    Map<String, Object> getToken(@Body String body);
  }
}