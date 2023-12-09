package com.adventofcode.input.day8;

public record Node(String id, String left, String right) {

    public static Node parse(String line){
        String[] id = line.split("=");
        String[] leftRight = id[1].replace("(", "").replace(")", "").split(", ");
        return new Node(id[0].trim(), leftRight[0].trim(), leftRight[1].trim());
    }
}