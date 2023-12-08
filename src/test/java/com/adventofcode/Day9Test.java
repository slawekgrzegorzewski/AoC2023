package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    static Day9 day9;

    @BeforeAll
    public static void init() throws IOException {
        day9 = new Day9();
    }

    @Test
    void testPart1() {
        int part1Result = day9.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(0, part1Result);
    }

    @Test
    void testPart2() {
        int part2Result = day9.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(0, part2Result);
    }
}