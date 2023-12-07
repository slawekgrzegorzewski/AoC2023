package com.adventofcode.input.day2;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Game(int id, Draw[] draws) {
    private static final Pattern gameLine = Pattern.compile("Game ([0-9]*): (.*)");

    public static Game parse(String line) {
        Matcher matcher = gameLine.matcher(line);
        if (!matcher.find()) {
            throw new RuntimeException("Could not parse a line " + line);
        }
        return new Game(
                Integer.parseInt(matcher.group(1)),
                Arrays.stream(matcher.group(2).split(";"))
                        .map(Draw::parse)
                        .toArray(Draw[]::new)
        );
    }

    public Draw findMinSetOfCubes() {
        return new Draw(
                Arrays.stream(draws).mapToInt(Draw::red).max().orElseThrow(),
                Arrays.stream(draws).mapToInt(Draw::green).max().orElseThrow(),
                Arrays.stream(draws).mapToInt(Draw::blue).max().orElseThrow()
        );
    }
}

