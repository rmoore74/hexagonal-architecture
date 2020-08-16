package io.rogermoore.hexagonal.user;

import javax.inject.Named;
import java.util.UUID;

@Named
public class UserAdapter {

    public RestGetUserDetailsResponse adapt(final User user) {
        return RestGetUserDetailsResponse.builder()
                .withUserUuid(user.getUserUuid())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withAge(user.getAge())
                .build();
    }

    public User adapt(final RestCreateUserRequest createUserRequest) {
        return User.builder()
                .withUserUuid(UUID.randomUUID())
                .withFirstName(createUserRequest.getFirstName())
                .withLastName(createUserRequest.getLastName())
                .withAge(createUserRequest.getAge())
                .build();
    }

}
