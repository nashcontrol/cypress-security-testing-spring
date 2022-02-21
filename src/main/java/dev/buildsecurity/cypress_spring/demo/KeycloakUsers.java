package dev.buildsecurity.cypress_spring.demo;

import java.util.HashMap;
import java.util.Map;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class KeycloakUsers implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(KeycloakUsers.class);

    @Value("${embedded.keycloak.auth-server-url}")
    private String keycloak_auth_server_uri;

    @Value("${keycloak.realm}")
    private String keycloak_realm;

    @Value("${keycloak.resource}")
    private String keycloak_client;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("test", "pass");
        map.put("admin", "complexpass");

        map.forEach((username, password) -> {
            Keycloak keycloak = KeycloakBuilder.builder().serverUrl(keycloak_auth_server_uri)
                    .grantType(OAuth2Constants.PASSWORD).realm(keycloak_realm).clientId(keycloak_client)
                    .username(username).password(password)
                    .build();

            AccessTokenResponse token = keycloak.tokenManager().getAccessToken();
            logger.info("JWT for user {} : {}", username, token.getToken());
        });

    }
}
