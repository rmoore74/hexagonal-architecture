package io.rogermoore.hexagonal.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final UUID USER_UUID = UUID.randomUUID();

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setup() {
        userController = new UserController(userService);
    }

    @Test
    void createUser() {
        RestCreateUserRequest request = mock(RestCreateUserRequest.class);
        given(userService.createUser(request)).willReturn(USER_UUID);

        ResponseEntity<UUID> result = userController.createUser(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(USER_UUID);
    }

    @Test
    void getUserDetails() {
        RestGetUserDetailsResponse response = mock(RestGetUserDetailsResponse.class);
        given(userService.getUserDetails(USER_UUID)).willReturn(response);

        ResponseEntity<RestGetUserDetailsResponse> result =userController.getUserDetails(USER_UUID.toString());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

}
