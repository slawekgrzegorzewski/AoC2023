package com.adventofcode.input.day10;

import com.adventofcode.input.Coordinates;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    private final Coordinates coordinates;
    private final Set<Node> nodes;

    public Node(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.nodes = new HashSet<>();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Set<Node> getNodes() {
        return new HashSet<>(nodes);
    }

    public void addNode(Node node){
        nodes.add(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(coordinates, node.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
