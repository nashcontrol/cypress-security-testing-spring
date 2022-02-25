package dev.buildsecurity.cypress.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("Alive!");
    }

    @PreAuthorize("hasRole('Reader')")
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(Authentication authentication) {
        return ResponseEntity.ok("Hello " + authentication.getName());
    }

}
