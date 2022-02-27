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
import dev.buildsecurity.cypress.demo.controller.AdminController;
import dev.buildsecurity.cypress.demo.controller.UserController;

@WebMvcTest(AdminController.class)
@Import({KeycloakConfig.class, KeycloakSecurityConfig.class})
@ActiveProfiles("integration-tests")
public class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldNotAllowAnonymousAccesstoMe() throws Exception {
        mockMvc.perform(get("/api/admin/me"))
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
    public void shouldNotAllowAuthenticatedAccesstoMeForNonAdmins() throws Exception {
        mockMvc.perform(get("/api/admin/me"))
        .andExpect(status().isForbidden());
        
    }
}