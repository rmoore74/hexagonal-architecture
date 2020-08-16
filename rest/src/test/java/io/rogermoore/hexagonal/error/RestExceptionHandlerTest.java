package io.rogermoore.hexagonal.error;

import io.rogermoore.hexagonal.command.UnsupportedCommandException;
import io.rogermoore.hexagonal.event.EventNotFoundException;
import io.rogermoore.hexagonal.event.InvalidEventTypeException;
import io.rogermoore.hexagonal.query.UnsupportedQueryException;
import io.rogermoore.hexagonal.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    @Mock
    private WebRequest request;

    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void setup() {
        restExceptionHandler = new RestExceptionHandler();
    }

    @Test
    void handleUserNotFound() {
        ResponseEntity<ErrorResponse> result = restExceptionHandler.handleUserNotFound(mock(UserNotFoundException.class), request);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getBody().getMessage()).isEqualTo("User does not exist.");
    }

    @Test
    void handleEventNotFound() {
        ResponseEntity<ErrorResponse> result = restExceptionHandler.handleEventNotFound(mock(EventNotFoundException.class), request);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getBody().getMessage()).isEqualTo("Event does not exist.");
    }

    @Test
    void handleInvalidEventType() {
        ResponseEntity<ErrorResponse> result = restExceptionHandler.handleInvalidEventType(mock(InvalidEventTypeException.class), request);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody().getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(result.getBody().getMessage()).isEqualTo("Invalid event type.");
    }

    @Test
    void handleUnsupportedQuery() {
        ResponseEntity<ErrorResponse> result = restExceptionHandler.handleUnsupportedQuery(mock(UnsupportedQueryException.class), request);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody().getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(result.getBody().getMessage()).isEqualTo("Invalid query.");
    }

    @Test
    void handleUnsupportedCommand() {
        ResponseEntity<ErrorResponse> result = restExceptionHandler.handleUnsupportedCommand(mock(UnsupportedCommandException.class), request);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody().getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(result.getBody().getMessage()).isEqualTo("Invalid command.");
    }

}
