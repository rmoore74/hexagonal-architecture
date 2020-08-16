package io.rogermoore.hexagonal.query;

import java.util.List;

public interface QueryHandler<T, K extends Query> {
    List<T> handle(K query);
}
