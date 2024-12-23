package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    static Day12 day12;

    @BeforeAll
    public static void init() throws IOException {
        day12 = new Day12();
    }

    @Test
    void testPart1() {
        long part1Result = day12.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(7118, part1Result);
    }

    @Test
    void testPart2() {
        long part2Result = day12.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(7030194981795L, part2Result);
    }
}