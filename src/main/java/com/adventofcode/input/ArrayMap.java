package com.adventofcode.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ArrayMap<T> {
    private final T[][] map;
    private Coordinates coordinates = new Coordinates(0, 0);

    public ArrayMap(T[][] map) {
        this.map = map;
    }

    public ArrayMap<T> goTo(Coordinates coordinates) {
        if (isInBounds(coordinates)) this.coordinates = coordinates;
        else throw new RuntimeException();
        return this;
    }

    public int getWidth() {
        return this.map[0].length;
    }

    public int getHeight() {
        return this.map.length;
    }

    public boolean isInBounds(Coordinates c) {
        return c.x() >= 0 && c.x() < getHeight() && c.y() >= 0 && c.y() < getWidth();
    }

    public T getValue() {
        return getValue(coordinates);
    }

    private T getValue(Coordinates coordinates) {
        return map[coordinates.x()][coordinates.y()];
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean canGoUp() {
        return isInBounds(coordinates.up());
    }

    public boolean canGoDown() {
        return isInBounds(coordinates.down());
    }

    public boolean canGoLeft() {
        return isInBounds(coordinates.left());
    }

    public boolean canGoRight() {
        return isInBounds(coordinates.right());
    }

    public ArrayMap<T> up() {
        if (canGoUp())
            coordinates = coordinates.up();
        else throw new RuntimeException();
        return this;
    }

    public ArrayMap<T> down() {
        if (canGoDown()) coordinates = coordinates.down();
        else throw new RuntimeException();
        return this;
    }

    public ArrayMap<T> left() {
        if (canGoLeft()) coordinates = coordinates.left();
        else throw new RuntimeException();
        return this;
    }

    public ArrayMap<T> right() {
        if (canGoRight()) coordinates = coordinates.right();
        else throw new RuntimeException();
        return this;
    }

    public Optional<T> peek(Coordinates coordinates) {
        return Optional.ofNullable(isInBounds(coordinates) ? getValue(coordinates) : null);
    }

    public Optional<T> peekUp() {
        return peek(coordinates.up());
    }

    public Optional<T> peekDown() {
        return peek(coordinates.down());
    }

    public Optional<T> peekLeft() {

        return peek(coordinates.left());
    }

    public Optional<T> peekRight() {
        return peek(coordinates.right());
    }

    public void traverse(Consumer<Coordinates> consumer, Runnable newLineIndicator) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Coordinates c = new Coordinates(i, j);
                goTo(c);
                consumer.accept(c);
            }
            newLineIndicator.run();
        }
    }

    public Stream<Element<T>> traverse() {
        List<Element<T>> elements = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                elements.add(new Element<>(new Coordinates(i, j), getValue(new Coordinates(i, j))));
            }
        }
        return elements.stream();
    }

    public Stream<Element<T>> traverseRow(int x) {
        List<Element<T>> row = new ArrayList<>();
        for (int j = 0; j < map[x].length; j++) {
            row.add(new Element<>(new Coordinates(x, j), getValue(new Coordinates(x, j))));
        }
        return row.stream();
    }

    public Stream<Element<T>> traverseColumn(int y) {
        List<Element<T>> column = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            column.add(new Element<>(new Coordinates(i, y), getValue(new Coordinates(i, y))));
        }
        return column.stream();
    }

    public Optional<Coordinates> findFirst(Predicate<T> test) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (test.test(map[i][j])) {
                    return Optional.of(new Coordinates(i, j));
                }
            }
        }
        return Optional.empty();
    }

    public void print() {
        traverse(
                coordinates -> System.out.print(getValue(coordinates)),
                System.out::println
        );
    }

    public record Element<T>(Coordinates coordinates, T value) {
    }
}
