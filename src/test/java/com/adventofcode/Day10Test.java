package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

    static Day10 day10;

    @BeforeAll
    public static void init() throws IOException {
        day10 = new Day10();
    }

    @Test
    void testPart1() {
        int part1Result = day10.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(7086, part1Result);
    }

    @Test
    void testPart2() {
        int part2Result = day10.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(317, part2Result);
    }
}