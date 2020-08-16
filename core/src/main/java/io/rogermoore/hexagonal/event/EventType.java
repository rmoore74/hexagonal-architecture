package io.rogermoore.hexagonal.event;

import io.rogermoore.hexagonal.command.Command;
import io.rogermoore.hexagonal.event.query.GetAllEventsQuery;
import io.rogermoore.hexagonal.event.query.GetEventQuery;
import io.rogermoore.hexagonal.query.Query;
import io.rogermoore.hexagonal.user.command.CreateUserCommand;
import io.rogermoore.hexagonal.user.query.GetUserDetailsQuery;

public enum EventType {

    CREATE_USER,
    GET_USER_DETAILS,
    GET_ALL_EVENTS,
    GET_EVENT,
    FAILED_COMMAND,
    FAILED_QUERY;

    public static EventType from(Command command) {
        if (command instanceof CreateUserCommand) {
            return CREATE_USER;
        }
        throw new InvalidEventTypeException(String.format("Event type for %s not supported", command.getClass().getName()));
    }

    public static EventType from(Query query) {
        if (query instanceof GetUserDetailsQuery) {
            return GET_USER_DETAILS;
        }
        if (query instanceof GetAllEventsQuery) {
            return GET_ALL_EVENTS;
        }
        if (query instanceof GetEventQuery) {
            return GET_EVENT;
        }
        throw new InvalidEventTypeException(String.format("Event type for %s not supported", query.getClass().getName()));
    }

}
