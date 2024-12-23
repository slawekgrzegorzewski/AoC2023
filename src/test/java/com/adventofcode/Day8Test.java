package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {

    static Day8 day8;

    @BeforeAll
    public static void init() throws IOException {
        day8 = new Day8();
    }

    @Test
    void testPart1() {
        int part1Result = day8.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(14429, part1Result);
    }

    @Test
    void testPart2() {
        long part2Result = day8.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(10_921_547_990_923L, part2Result);
    }
}