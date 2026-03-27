package io.github.kevinpan45.common.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@Slf4j
@Order(1)
public class ActuatorSecurityConfiguration {

    @Value("${management.endpoints.web.base-path:/actuator}")
    private String actuatorBasePath;

    @Value(
            "${management.security.allowed-ips:127.0.0.1,::1,10.0.0.0/8,172.16.0.0/12,192.168.0.0/16}")
    private List<String> allowedIps;

    @Bean
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(actuatorBasePath + "/**")
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers(actuatorBasePath + "/**")
                                        .access(ipBasedAuthorizationManager()))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    private AuthorizationManager<RequestAuthorizationContext> ipBasedAuthorizationManager() {
        return (authentication, context) -> {
            HttpServletRequest request = context.getRequest();
            String clientIp = getClientIp(request);

            boolean allowed =
                    allowedIps.stream()
                            .anyMatch(
                                    allowedIp -> {
                                        try {
                                            IpAddressMatcher matcher =
                                                    new IpAddressMatcher(allowedIp.trim());
                                            return matcher.matches(request);
                                        } catch (IllegalArgumentException e) {
                                            log.warn(
                                                    "Invalid IP pattern in configuration: {}",
                                                    allowedIp);
                                            return false;
                                        }
                                    });

            if (!allowed) {
                log.warn("Actuator access denied for IP: {}", clientIp);
            } else {
                log.debug("Actuator access granted for IP: {}", clientIp);
            }

            return new AuthorizationDecision(allowed);
        };
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        return request.getRemoteAddr();
    }
}
