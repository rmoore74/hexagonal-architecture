package io.rogermoore.hexagonal.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UUID> createUser(@RequestBody final RestCreateUserRequest createUserRequest) {
        LOGGER.info("Creating user");
        UUID newUserUuid = userService.createUser(createUserRequest);
        return ResponseEntity.ok(newUserUuid);
    }

    @GetMapping("/user/{userUuid}")
    public ResponseEntity<RestGetUserDetailsResponse> getUserDetails(@PathVariable final String userUuid) {
        LOGGER.info("Retrieving user details for {}", userUuid);
        return ResponseEntity.ok(userService.getUserDetails(UUID.fromString(userUuid)));
    }

}