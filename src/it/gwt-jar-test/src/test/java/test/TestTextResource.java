package test;

import walkingkooka.resource.TextResource;

public final class TestTextResource implements TextResource {

    @Override
    public String text() {
        return "Hello";
    }
}