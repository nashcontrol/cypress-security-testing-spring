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
@PreAuthorize("hasRole('Writer')")
@RequestMapping(value = "/api/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<String> getAdmin(Authentication authentication) {
        return ResponseEntity.ok("Hello admin, you are" + authentication.getName());
    }

}
