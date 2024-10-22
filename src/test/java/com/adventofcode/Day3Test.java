package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    static Day3 day3;

    @BeforeAll
    public static void init() throws IOException {
        day3 = new Day3();
    }

    @Test
    void testPart1() {
        int part1Result = day3.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(526404, part1Result);
    }

    @Test
    void testPart2() {
        long part2Result = day3.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(84399773, part2Result);
    }
}