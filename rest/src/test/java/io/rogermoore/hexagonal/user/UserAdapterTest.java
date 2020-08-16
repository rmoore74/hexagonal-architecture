package io.rogermoore.hexagonal.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserAdapterTest {

    private static final UUID USER_UUID = UUID.randomUUID();
    private static final RestGetUserDetailsResponse GET_USER_DETAILS_RESPONSE = RestGetUserDetailsResponse.builder()
            .withUserUuid(USER_UUID)
            .withFirstName("test")
            .withLastName("user")
            .withAge(10)
            .build();
    private static final RestCreateUserRequest CREATE_USER_REQUEST = RestCreateUserRequest.builder()
            .withFirstName("test")
            .withLastName("user")
            .withAge(10)
            .build();
    private static final User USER = User.builder()
            .withUserUuid(USER_UUID)
            .withFirstName("test")
            .withLastName("user")
            .withAge(10)
            .build();

    private UserAdapter userAdapter;

    @BeforeEach
    void setup() {
        userAdapter = new UserAdapter();
    }

    @Test
    void adaptUserToGetUserDetailsResponse() {
        assertThat(userAdapter.adapt(USER)).isEqualTo(GET_USER_DETAILS_RESPONSE);
    }

    @Test
    void adaptCreateUserRequestToUser() {
        assertThat(userAdapter.adapt(CREATE_USER_REQUEST)).isEqualToIgnoringGivenFields(USER, "userUuid");
    }

}
