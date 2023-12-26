package io.github.kevinpan45.common.iam.context;

import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtProvider {
    IamContext handle(Jwt jwt);
}
