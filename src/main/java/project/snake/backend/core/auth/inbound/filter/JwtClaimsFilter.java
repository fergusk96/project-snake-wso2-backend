package project.snake.backend.core.auth.inbound.filter;


import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.http.filter.FilterContinuation;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import java.security.interfaces.RSAPublicKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServerFilter(patterns = { "/**" })
public class JwtClaimsFilter implements Ordered {

  @Value("${micronaut.jwt.jwk.url}")
  private String jwkUrl;

  @RequestFilter
  @ExecuteOn(TaskExecutors.BLOCKING)
  public void doFilter(
    HttpRequest<?> request,
    FilterContinuation<MutableHttpResponse<?>> continuation) throws JwkException {
    String authHeader = request.getHeaders().get("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      DecodedJWT jwt = JWT.decode(token);
      if(!jwt.getIssuer().equals(jwkUrl)) {
        throw new JWTVerificationException("Incorrect token issuer");
      }
      Jwk jwk = getJwk(jwt);
      Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
      JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(jwkUrl).build();
      jwtVerifier.verify(token);
      request.setAttribute("userId", jwt.getClaim("sub").asString());
      continuation.proceed();
    }
  }

  private Jwk getJwk(final DecodedJWT jwt) throws JwkException {
    JwkProvider provider = new UrlJwkProvider(jwkUrl);
    return provider.get(jwt.getKeyId());
  }
}
