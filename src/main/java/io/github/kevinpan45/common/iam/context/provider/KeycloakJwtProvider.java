package io.github.kevinpan45.common.iam.context.provider;

import org.springframework.security.oauth2.jwt.Jwt;

import io.github.kevinpan45.common.iam.context.IamContext;
import io.github.kevinpan45.common.iam.context.JwtProvider;

public class KeycloakJwtProvider implements JwtProvider {

    @Override
    public IamContext handle(Jwt jwt) {
        IamContext iamContext = new IamContext();
        iamContext.setUsername(jwt.getClaimAsString("preferred_username")).setEmail(jwt.getClaimAsString("email"))
                .setSub(jwt.getSubject());
        return iamContext;
    }

}
