package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day8.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;

public class Day8 {

    private final String instructions;
    private final Map<String, Node> nodes;

    public Day8() throws IOException {
        List<String> lines = Input.day8();
        nodes = new HashMap<>();
        instructions = lines.get(0);
        for (int i = 2; i < lines.size(); i++) {
            Node n = Node.parse(lines.get(i));
            nodes.put(n.id(), n);
        }
    }

    int part1() {
        return instructions.length() * findNumberOfCycles(
                nodes.get("AAA"), n -> "ZZZ".equals(n.id())
        );
    }

    private int findNumberOfCycles(Node node, Predicate<Node> finishPredicate) {
        int cycles = 0;
        while (!finishPredicate.test(node)) {
            for (char instruction : instructions.toCharArray()) {
                if (instruction == 'L') {
                    node = nodes.get(node.left());
                } else {
                    node = nodes.get(node.right());
                }
            }
            cycles++;
        }
        return cycles;
    }

    long part2() {
        return instructions.length() *
                nodes.keySet().stream()
                        .filter(id -> id.endsWith("A"))
                        .map(nodes::get)
                        .mapToLong(node -> findNumberOfCycles(node, n -> n.id().endsWith("Z")))
                        .reduce(1, (a, b) -> a * b);
    }

}


