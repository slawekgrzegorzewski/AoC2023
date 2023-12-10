package com.adventofcode.input.day10;

import java.util.Arrays;
import java.util.Optional;

public enum PipeType {
    VERTICAL('|', false, false, true, true),
    HORIZONTAL('-', true, true, false, false),
    NE('L', true, false, true, false),
    NW('J', false, true, true, false),
    SW('7', false, true, false, true),
    SE('F', true, false, false, true);
    private final char symbol;
    private final boolean connectsToRight;
    private final boolean connectsToLeft;
    private final boolean connectsToUp;
    private final boolean connectsToDown;

    PipeType(char symbol, boolean connectsToRight, boolean connectsToLeft, boolean connectsToUp, boolean connectsToDown) {
        this.symbol = symbol;
        this.connectsToRight = connectsToRight;
        this.connectsToLeft = connectsToLeft;
        this.connectsToUp = connectsToUp;
        this.connectsToDown = connectsToDown;
    }

    public static Optional<PipeType> find(char symbol) {
        return Arrays.stream(PipeType.values()).filter(pt -> pt.symbol == symbol).findAny();
    }

    public boolean connectsToRight() {
        return connectsToRight;
    }

    public boolean connectsToLeft() {
        return connectsToLeft;
    }

    public boolean connectsToUp() {
        return connectsToUp;
    }

    public boolean connectsToDown() {
        return connectsToDown;
    }
}
