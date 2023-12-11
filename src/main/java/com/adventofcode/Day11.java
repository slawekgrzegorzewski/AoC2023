package com.adventofcode;

import com.adventofcode.input.ArrayMap;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day11 {

    private final ArrayMap<Character> galaxiesMap;

    public Day11() throws IOException {
        galaxiesMap = Input.day11();
    }

    long part1() {
        return addAllDistances(1);
    }

    long part2() {
        return addAllDistances(999_999);
    }

    private long addAllDistances(int agingPace) {
        List<Integer> rowsToExpand = new ArrayList<>(Collections.nCopies(galaxiesMap.getHeight(), 0));
        List<Integer> columnsToExpand = new ArrayList<>(Collections.nCopies(galaxiesMap.getWidth(), 0));
        IntStream.range(0, galaxiesMap.getHeight())
                .filter(this::rowHasNoGalaxy)
                .forEach(i -> rowsToExpand.set(i, agingPace));
        IntStream.range(0, galaxiesMap.getWidth())
                .filter(this::columnHasNoGalaxy)
                .forEach(i -> columnsToExpand.set(i, agingPace));
        List<ArrayMap.Element<Character>> galaxies = galaxiesMap.traverse()
                .filter(e -> e.value().equals('#'))
                .toList();
        List<Long> distances = new ArrayList<>();
        for (int i = 0; i < galaxies.size() - 1; i++) {
            ArrayMap.Element<Character> from = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                ArrayMap.Element<Character> to = galaxies.get(j);
                int minX = Math.min(from.coordinates().x(), to.coordinates().x());
                int maxX = Math.max(from.coordinates().x(), to.coordinates().x());
                int minY = Math.min(from.coordinates().y(), to.coordinates().y());
                int maxY = Math.max(from.coordinates().y(), to.coordinates().y());
                long distance = maxX + maxY - minX - minY
                        + (minX == maxX ? 0 : rowsToExpand.subList(minX + 1, maxX).stream().mapToInt(x -> x).sum())
                        + (minY == maxY ? 0 : columnsToExpand.subList(minY + 1, maxY).stream().mapToInt(y -> y).sum());
                distances.add(distance);
            }
        }
        return distances.stream().mapToLong(d -> d).sum();
    }

    private boolean rowHasNoGalaxy(int x) {
        return galaxiesMap.traverseRow(x)
                .map(ArrayMap.Element::value)
                .allMatch(c -> c.equals('.'));
    }

    private boolean columnHasNoGalaxy(int y) {
        return galaxiesMap.traverseColumn(y)
                .map(ArrayMap.Element::value)
                .allMatch(c -> c.equals('.'));
    }

}


