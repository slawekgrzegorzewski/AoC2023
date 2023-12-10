package com.adventofcode.input;

public record Coordinates(int x, int y) {
    public Coordinates up() {
        return new Coordinates(x - 1, y);
    }

    public Coordinates down() {
        return new Coordinates(x + 1, y);
    }

    public Coordinates left() {
        return new Coordinates(x, y - 1);
    }

    public Coordinates right() {
        return new Coordinates(x, y + 1);
    }

}
