package io.github.kevinpan45.common.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import io.github.kevinpan45.common.iam.context.IamContext;
import io.github.kevinpan45.common.iam.context.IamContextHolder;
import io.github.kevinpan45.common.iam.context.JwtProvider;
import io.github.kevinpan45.common.iam.exception.IamContextInitException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationEvents {

  @Autowired
  private Map<String, JwtProvider> issuerTokenProviderMap = new HashMap<>();

  @EventListener
  public void onSuccess(AuthenticationSuccessEvent success) {
    Jwt jwt = (Jwt) success.getAuthentication().getPrincipal();
    String issuer = jwt.getIssuer().toString();
    JwtProvider provider = issuerTokenProviderMap.get(issuer);
    if (provider != null) {
      IamContext iamContext = provider.handle(jwt);
      if (log.isTraceEnabled()) {
        log.trace("User {} loged.", iamContext.getUsername());
      }
      IamContextHolder.setContext(iamContext);
    } else {
      log.error("No JwtUserHandler found for issuer: {}", issuer);
      throw new IamContextInitException("No JwtUserHandler found for issuer: " + issuer);
    }
  }

  @EventListener
  public void onFailure(AbstractAuthenticationFailureEvent failures) {
    // ...
  }
}