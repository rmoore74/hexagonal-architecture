package io.rogermoore.hexagonal.query;

public class UnsupportedQueryException extends RuntimeException {
    public UnsupportedQueryException(final Query query) {
        super(String.format("Unsupported query: %s", query.getClass().getTypeName()));
    }
}
