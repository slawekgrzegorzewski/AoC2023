package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Day12 {
    private final String[] conditionsMap;
    private final int[][] contiguousMap;
    private final HashMap<String, Long> cache = new HashMap<>();

    public Day12() throws IOException {
        List<String> lines = Input.day12();
        conditionsMap = new String[lines.size()];
        contiguousMap = new int[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(" ");
            conditionsMap[i] = parts[0];
            contiguousMap[i] = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).toArray();
        }
        int max = 0;
        for (int j = 0; j < contiguousMap.length; j++) {
            int[] m = contiguousMap[j];
            int i = conditionsMap[j].length() - (IntStream.of(m).sum() + m.length - 1) + 1;
            if (i > max) max = i;
        }
    }

    record TaskInput(String row, int[] contiguousAreas) {
    }

    long part1() {
        return findAllArrangements(i -> new TaskInput(conditionsMap[i], contiguousMap[i]));
    }

    long part2() {
        return findAllArrangements(i -> new TaskInput(
                String.join("?", Collections.nCopies(5, conditionsMap[i])),
                Collections.nCopies(5, contiguousMap[i]).stream().flatMapToInt(Arrays::stream).toArray()));
    }

    private long findAllArrangements(Function<Integer, TaskInput> prepareTaskInput) {
        cache.clear();
        long sum = 0;
        for (int i = 0; i < conditionsMap.length; i++) {
            TaskInput taskInput = prepareTaskInput.apply(i);
            sum += findAllArrangements(taskInput.row(), taskInput.contiguousAreas());
        }
        return sum;
    }

    private long findAllArrangements(String row, int[] contiguousAreas) {
        String line = row + " " + String.join(",", IntStream.of(contiguousAreas).mapToObj(Objects::toString).toArray(String[]::new));
        if (cache.containsKey(line)) return cache.get(line);
        if (contiguousAreas.length == 0) return row.contains("#") ? 0 : 1;
        if (row.length() - IntStream.of(contiguousAreas).sum() - contiguousAreas.length + 1 < 0) return 0;
        boolean damagedOrUnknownOnly = !row.substring(0, contiguousAreas[0]).contains(".");
        if (row.length() == contiguousAreas[0]) return damagedOrUnknownOnly ? 1 : 0;
        long count = (
                row.charAt(0) != '#'
                        ? findAllArrangements(trimStart(row.substring(1)), contiguousAreas)
                        : 0)
                + (
                damagedOrUnknownOnly && row.charAt(contiguousAreas[0]) != '#'
                        ? findAllArrangements(trimStart(row.substring(contiguousAreas[0] + 1)), slice(contiguousAreas))
                        : 0);
        return cache.computeIfAbsent(line, k -> count);
    }

    private String trimStart(String string) {
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != '.'
            ) return string.substring(i);
        }
        return string;
    }

    private int[] slice(int[] contiguousAreas) {
        return IntStream.of(contiguousAreas).skip(1).toArray();
    }

}


