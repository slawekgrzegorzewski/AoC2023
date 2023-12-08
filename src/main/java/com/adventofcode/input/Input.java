package com.adventofcode.input;

import com.adventofcode.input.day2.Game;
import com.adventofcode.input.day4.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Input {

    public static List<String> day1() throws IOException {
        return getInputFromFile("/day1");
    }

    public static List<Game> day2() throws IOException {
        return getInputFromFile("/day2")
                .stream()
                .map(Game::parse)
                .toList();
    }

    public static char[][] day3() throws IOException {
        return getInputFromFile("/day3")
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public static List<Card> day4() throws IOException {
        return getInputFromFile("/day4")
                .stream()
                .map(Card::parse)
                .toList();
    }

    public static List<String> day5() throws IOException {
        return getInputFromFile("/day5");
    }
    public static List<String> day6() throws IOException {
        return getInputFromFile("/day6");
    }

    private static List<String> getInputFromFile(String resourceName) throws IOException {
        try (InputStreamReader in = new InputStreamReader(Objects.requireNonNull(Input.class.getResourceAsStream(resourceName))); BufferedReader reader = new BufferedReader(in)) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}
