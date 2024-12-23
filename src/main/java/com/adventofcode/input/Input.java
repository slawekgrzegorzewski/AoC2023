package com.adventofcode.input;

import com.adventofcode.input.day2.Game;
import com.adventofcode.input.day4.Card;
import com.adventofcode.input.day5.Almanac;

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

    public static Almanac day5() throws IOException {
        return Almanac.parse(getInputFromFile("/day5"));
    }

    public static List<String> day6() throws IOException {
        return getInputFromFile("/day6");
    }

    public static List<String> day7() throws IOException {
        return getInputFromFile("/day7");
    }

    public static List<String> day8() throws IOException {
        return getInputFromFile("/day8");
    }

    public static List<String> day9() throws IOException {
        return getInputFromFile("/day9");
    }

    public static ArrayMap<Character> day10() throws IOException {
        List<String> lines = getInputFromFile("/day10");
        Character[][] map = new Character[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            char[] chars = lines.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                map[i][j] = chars[j];
            }
        }
        return new ArrayMap<>(map);
    }

    public static ArrayMap<Character> day11() throws IOException {
        List<String> lines = getInputFromFile("/day11");
        Character[][] map = new Character[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            char[] chars = lines.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                map[i][j] = chars[j];
            }
        }
        return new ArrayMap<>(map);
    }

    public static List<String> day12() throws IOException {
        return getInputFromFile("/day12");
    }

    public static List<String> day13() throws IOException {
        return getInputFromFile("/day13");
    }

    public static List<String> day14() throws IOException {
        return getInputFromFile("/day14");
    }

    private static List<String> getInputFromFile(String resourceName) throws IOException {
        try (InputStreamReader in = new InputStreamReader(Objects.requireNonNull(Input.class.getResourceAsStream(resourceName))); BufferedReader reader = new BufferedReader(in)) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}
