package com.adventofcode;

import com.adventofcode.input.ArrayMap;
import com.adventofcode.input.Input;
import com.adventofcode.input.Pair;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 {

    private final ArrayMap<Character> galaxiesMap;
    private final List<Integer> rowsToExpand;
    private final List<Integer> columnsToExpand;
    private final List<ArrayMap.Element<Character>> galaxies;

    public Day11() throws IOException {
        galaxiesMap = Input.day11();
        rowsToExpand = new ArrayList<>(Collections.nCopies(galaxiesMap.getHeight(), 0));
        columnsToExpand = new ArrayList<>(Collections.nCopies(galaxiesMap.getWidth(), 0));
        IntStream.range(0, galaxiesMap.getHeight())
                .filter(this::rowHasNoGalaxy)
                .forEach(i -> rowsToExpand.set(i, 1));
        IntStream.range(0, galaxiesMap.getWidth())
                .filter(this::columnHasNoGalaxy)
                .forEach(i -> columnsToExpand.set(i, 1));
        galaxies = galaxiesMap.traverse()
                .filter(e -> e.value().equals('#'))
                .toList();
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

    long part1() {
        return addAllDistances(1);
    }

    long part2() {
        return addAllDistances(999_999);
    }

    private long addAllDistances(long agingPace) {
        return IntStream.range(0, galaxies.size())
                .mapToObj(i -> IntStream.range(i + 1, galaxies.size()).mapToObj(j -> new Pair<>(i, j)))
                .flatMap(Function.identity())
                .map(indices -> new Pair<>(galaxies.get(indices.first()), galaxies.get(indices.second())))
                .mapToLong(pair -> {
                    int minX = Math.min(pair.first().coordinates().x(), pair.second().coordinates().x());
                    int maxX = Math.max(pair.first().coordinates().x(), pair.second().coordinates().x());
                    int minY = Math.min(pair.first().coordinates().y(), pair.second().coordinates().y());
                    int maxY = Math.max(pair.first().coordinates().y(), pair.second().coordinates().y());
                    int emptyRowsAndColumnsInBetweenGalaxies = Stream.concat(
                                    (minX == maxX ? Stream.empty() : rowsToExpand.subList(minX + 1, maxX).stream()),
                                    (minY == maxY ? Stream.empty() : columnsToExpand.subList(minY + 1, maxY).stream())
                            )
                            .mapToInt(i -> i)
                            .sum();
                    return maxX + maxY - minX - minY + emptyRowsAndColumnsInBetweenGalaxies * agingPace;
                })
                .sum();
    }

}


