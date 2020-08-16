package io.rogermoore.hexagonal;

import io.rogermoore.hexagonal.user.User;
import io.rogermoore.hexagonal.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    private static final UUID USER_UUID = UUID.randomUUID();

    @Mock
    private User user;

    @Mock
    private ConcurrentMap<UUID, User> userStore;

    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepository(userStore);
    }

    @Test
    void add() {
        given(user.getUserUuid()).willReturn(USER_UUID);

        userRepository.add(user);

        verify(userStore).put(USER_UUID, user);
    }

    @Test
    void find() {
        given(userStore.get(USER_UUID)).willReturn(user);

        User result = userRepository.find(USER_UUID);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void find_nonExistantUser() {
        Throwable thrown = catchThrowable(() -> userRepository.find(USER_UUID));

        assertThat(thrown).isInstanceOf(UserNotFoundException.class);
        assertThat(thrown).hasMessage(String.format("User %s does not exist", USER_UUID));
    }

}
