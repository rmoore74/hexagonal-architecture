package io.rogermoore.hexagonal.event;

import com.google.gson.Gson;
import io.rogermoore.hexagonal.command.Command;
import io.rogermoore.hexagonal.query.Query;

import java.util.UUID;

public class EventFactory {

    private static final Gson MAPPER = new Gson();

    public static <T> Event from(Command command, T executed) {
        return new Event(UUID.randomUUID(), EventType.from(command), mapToJson(executed));
    }

    public static <T> Event from(Query query, T executed) {
        return new Event(UUID.randomUUID(), EventType.from(query), mapToJson(executed));
    }

    public static Event failed(Command command) {
        return new Event(UUID.randomUUID(), EventType.FAILED_COMMAND, mapToJson(command));
    }

    public static Event failed(Query query) {
        return new Event(UUID.randomUUID(), EventType.FAILED_QUERY, mapToJson(query));
    }

    private static <T> String mapToJson(T executed) {
            return MAPPER.toJson(executed);
    }

}
