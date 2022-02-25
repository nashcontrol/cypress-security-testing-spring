package dev.buildsecurity.cypress.demo;

import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.startsWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;




import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import dev.buildsecurity.cypress.demo.config.KeycloakConfig;
import dev.buildsecurity.cypress.demo.config.KeycloakSecurityConfig;
import dev.buildsecurity.cypress.demo.controller.UserController;

@WebMvcTest(UserController.class)
@Import({KeycloakConfig.class, KeycloakSecurityConfig.class})
@ActiveProfiles("integration-tests")
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void shouldAllowAnonymousAccessToHealth() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/user/health")).andReturn();
        assertEquals("Alive!", result.getResponse().getContentAsString());
    }


    @Test
    public void shouldNotAllowAnonymousAccesstoMe() throws Exception {
        mockMvc.perform(get("/api/user/me"))
        .andExpect(redirectedUrl("/sso/login"));
    }


    @Test
    @WithMockKeycloakAuth(
                authorities={"ROLE_Reader"},
                claims = @OpenIdClaims(
                    email = "test@buildsecurity.dev",
                    emailVerified = true,
                    nickName = "tester",
                    preferredUsername = "tester"))
    public void shouldAllowAuthenticatedAccesstoMe() throws Exception {
        mockMvc.perform(get("/api/user/me"))
        .andExpect(status().isOk())
				.andExpect(content().string(startsWith("Hello tester")));
        
    }

    //TODO: Fix keycloak bug
    // @WithMockUser(authorities={"find_permission"})
    // @Test
    // @WithMockKeycloakAuth(
    //         authorities = { "USER", "AUTHORIZED_PERSONNEL" },
    //         id = @IdTokenClaims(sub = "42"),
    //         oidc = @OidcStandardClaims(
    //                 email = "test@testing.com",
    //                 emailVerified = true,
    //                 nickName = "TesterNickName",
    //                 preferredUsername = "testName"),
    //         accessToken = @KeycloakAccessToken(
    //                 realmAccess = @KeycloakAccess(roles = { "TESTER" }),
    //                 authorization = @KeycloakAuthorization(
    //                         permissions = @KeycloakPermission(rsid = "toto", rsname = "truc", scopes = "abracadabra"))),
    //         privateClaims = @ClaimSet(stringClaims = @StringClaim(name = "foo", value = "bar")))
   
}