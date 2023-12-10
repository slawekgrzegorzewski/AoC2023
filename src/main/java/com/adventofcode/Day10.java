package com.adventofcode;

import com.adventofcode.input.ArrayMap;
import com.adventofcode.input.Coordinates;
import com.adventofcode.input.Direction;
import com.adventofcode.input.Input;
import com.adventofcode.input.day10.PipeType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day10 {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private static final Map<Integer, String> ANSI_COLORS = Map.of(0, "\u001B[31m", 1, "\u001B[32m", 2, "\u001B[33m", 3, "\u001B[34m", 4, "\u001B[35m", 5, "\u001B[36m");
    private final ArrayMap<Character> pipesMap;

    public Day10() throws IOException {
        pipesMap = Input.day10();
    }

    int part1() {
        return findCycle().size() / 2;
    }

    @NotNull
    private List<Coordinates> findCycle() {
        Coordinates animalPosition = pipesMap.findFirst(c -> c == 'S').orElseThrow();
        Coordinates source = null;
        Coordinates currentPosition = animalPosition;
        List<Coordinates> path = new ArrayList<>();
        do {
            pipesMap.goTo(currentPosition);
            Coordinates nextPosition = move(source);
            source = currentPosition;
            currentPosition = nextPosition;
            path.add(currentPosition);
        } while (!currentPosition.equals(animalPosition));
        return path;
    }

    private Coordinates move(@Nullable Coordinates source) {
        return Stream.of(
                        new Instructions(PipeType::connectsToRight, Coordinates::right),
                        new Instructions(PipeType::connectsToLeft, Coordinates::left),
                        new Instructions(PipeType::connectsToUp, Coordinates::up),
                        new Instructions(PipeType::connectsToDown, Coordinates::down)
                )
                .map(i -> i.perform(source, pipesMap.getCoordinates(), findPipeType(pipesMap.getCoordinates())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .orElseThrow();
    }

    private record Instructions(Predicate<PipeType> predicate, Function<Coordinates, Coordinates> nextNodeProvider) {
        public Optional<Coordinates> perform(@Nullable Coordinates source, Coordinates currentPosition, PipeType pipeType) {
            if (predicate().test(pipeType)) {
                Coordinates next = nextNodeProvider().apply(currentPosition);
                if (!next.equals(source)) {
                    return Optional.of(next);
                }
            }
            return Optional.empty();
        }
    }

    int part2() {
        List<Coordinates> path = findCycle();
        List<List<Coordinates>> compounds = findCompounds(path);
        List<List<Coordinates>> compoundsTouchingEdge = compounds
                .stream()
                .filter(this::compoundTouchingEdge)
                .toList();
        return compounds
                .stream()
                .filter(Predicate.not(compoundsTouchingEdge::contains))
                .filter(compound -> !canEscape(compound, path, compoundsTouchingEdge.stream().flatMap(List::stream).toList()))
                .mapToInt(List::size)
                .sum();
    }

    private List<List<Coordinates>> findCompounds(List<Coordinates> path) {
        List<List<Coordinates>> compounds = new ArrayList<>();
        Set<Coordinates> visitedNodes = new HashSet<>(path);
        pipesMap.traverse(c -> {
            if (!visitedNodes.contains(c)) {
                List<Coordinates> compound = findAllNodesBelongingToCompound(c, path);
                compounds.add(compound);
                visitedNodes.addAll(compound);
            }
        }, () -> {
        });
        return compounds;
    }

    private List<Coordinates> findAllNodesBelongingToCompound(Coordinates aroundNode, List<Coordinates> path) {
        List<Coordinates> compound = new ArrayList<>();
        Set<Coordinates> visitedNodes = new HashSet<>();
        Set<Coordinates> processing = new HashSet<>();
        processing.add(aroundNode);
        while (!processing.isEmpty()) {
            Coordinates node = processing.iterator().next();
            processing.remove(node);
            compound.add(node);
            visitedNodes.add(node);
            Stream.of(
                            node.left().up(),
                            node.up(),
                            node.right().up(),
                            node.left(),
                            node.right(),
                            node.left().down(),
                            node.down(),
                            node.right().down())
                    .filter(pipesMap::isInBounds)
                    .filter(Predicate.not(visitedNodes::contains))
                    .filter(Predicate.not(path::contains))
                    .forEach(processing::add);
        }
        return compound;
    }

    private boolean compoundTouchingEdge(List<Coordinates> coordinates) {
        return coordinates.stream().anyMatch(c -> c.x() == 0 || c.y() == 0);
    }

    private boolean canEscape(List<Coordinates> compound, List<Coordinates> path, List<Coordinates> nodesOutsideOfCycle) {
        Coordinates currentPosition = compound.stream().findAny().orElseThrow();
        while (!path.contains(currentPosition.up())) {
            currentPosition = currentPosition.up();
        }
        Coordinates startOnPath = currentPosition.up();
        MoveDescription move = new MoveDescription(
                startOnPath,
                currentPosition,
                switch (findPipeType(startOnPath)) {
                    case HORIZONTAL, NW -> Direction.LEFT;
                    case NE -> Direction.TOP;
                    default -> throw new RuntimeException();
                });
        do {
            move = move(move.currentPositionOnPath, move.direction);
            if (nodesOutsideOfCycle.contains(move.currentDotPosition))
                return true;
        } while (!startOnPath.equals(move.currentPositionOnPath));
        return false;
    }

    @NotNull
    private PipeType findPipeType(Coordinates c) {
        Coordinates coordinates = pipesMap.getCoordinates();
        pipesMap.goTo(c);
        Character value = pipesMap.getValue();
        PipeType pipeType = value == 'S' ? figureOutPipeType() : PipeType.find(value).orElseThrow();
        pipesMap.goTo(coordinates);
        return pipeType;
    }

    private PipeType figureOutPipeType() {
        List<Direction> directionsConnecting = new ArrayList<>();
        pipesMap.peekDown().flatMap(PipeType::find).filter(PipeType::connectsToUp).ifPresent(pt -> directionsConnecting.add(Direction.DOWN));
        pipesMap.peekUp().flatMap(PipeType::find).filter(PipeType::connectsToDown).ifPresent(pt -> directionsConnecting.add(Direction.TOP));
        pipesMap.peekLeft().flatMap(PipeType::find).filter(PipeType::connectsToRight).ifPresent(pt -> directionsConnecting.add(Direction.LEFT));
        pipesMap.peekRight().flatMap(PipeType::find).filter(PipeType::connectsToLeft).ifPresent(pt -> directionsConnecting.add(Direction.RIGHT));
        List<PipeType> candidates = Arrays.stream(PipeType.values()).toList().stream().filter(pt -> !directionsConnecting.contains(Direction.LEFT) || pt.connectsToLeft()).filter(pt -> !directionsConnecting.contains(Direction.RIGHT) || pt.connectsToRight()).filter(pt -> !directionsConnecting.contains(Direction.TOP) || pt.connectsToUp()).filter(pt -> !directionsConnecting.contains(Direction.DOWN) || pt.connectsToDown()).toList();
        if (candidates.size() != 1) {
            throw new RuntimeException();
        }
        return candidates.get(0);
    }

    private record MoveDescription(Coordinates currentPositionOnPath, Coordinates currentDotPosition,
                                   Direction direction) {
    }

    private MoveDescription move(Coordinates followedPathElement, Direction direction) {
        switch (direction) {
            case LEFT -> {
                Coordinates next = followedPathElement.left();
                return new MoveDescription(
                        next,
                        switch (findPipeType(next)) {
                            case HORIZONTAL -> next.down();
                            case NE -> next.left();
                            case SE -> next.right().down();
                            default -> throw new RuntimeException();
                        },
                        switch (findPipeType(next)) {
                            case HORIZONTAL -> Direction.LEFT;
                            case NE -> Direction.TOP;
                            case SE -> Direction.DOWN;
                            default -> throw new RuntimeException();
                        }
                );
            }
            case RIGHT -> {
                Coordinates next = followedPathElement.right();
                return new MoveDescription(
                        next,
                        switch (findPipeType(next)) {
                            case HORIZONTAL -> next.up();
                            case NW -> next.up().left();
                            case SW -> next.right();
                            default -> throw new RuntimeException();
                        },
                        switch (findPipeType(next)) {
                            case HORIZONTAL -> Direction.RIGHT;
                            case NW -> Direction.TOP;
                            case SW -> Direction.DOWN;
                            default -> throw new RuntimeException();
                        }
                );
            }
            case TOP -> {
                Coordinates next = followedPathElement.up();
                return new MoveDescription(
                        next,
                        switch (findPipeType(next)) {
                            case VERTICAL -> next.left();
                            case SW -> next.down().left();
                            case SE -> next.up();
                            default -> throw new RuntimeException();
                        },
                        switch (findPipeType(next)) {
                            case VERTICAL -> Direction.TOP;
                            case SW -> Direction.LEFT;
                            case SE -> Direction.RIGHT;
                            default -> throw new RuntimeException();
                        }
                );
            }
            case DOWN -> {
                Coordinates next = followedPathElement.down();
                return new MoveDescription(
                        next,
                        switch (findPipeType(next)) {
                            case VERTICAL -> next.right();
                            case NE -> next.up().right();
                            case NW -> next.down();
                            default -> throw new RuntimeException();
                        },
                        switch (findPipeType(next)) {
                            case VERTICAL -> Direction.DOWN;
                            case NE -> Direction.RIGHT;
                            case NW -> Direction.LEFT;
                            default -> throw new RuntimeException();
                        }
                );
            }
            default -> throw new RuntimeException();
        }
    }

    private void printMapWithCompounds(List<Coordinates> path, List<List<Coordinates>> compoundsSurroundedByPath) {
        pipesMap.traverse(c -> {
            if (path.contains(c)) {
                System.out.print(pipesMap.getValue());
            } else {
                boolean found = false;
                for (int i = 0; i < compoundsSurroundedByPath.size(); i++) {
                    if (compoundsSurroundedByPath.get(i).contains(c)) {
                        String ansiColor = ANSI_COLORS.get(i % ANSI_COLORS.size());
                        System.out.print(ansiColor + pipesMap.getValue() + ANSI_RESET);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.print(ANSI_WHITE + pipesMap.getValue() + ANSI_RESET);
                }
            }
        }, System.out::println);
    }

}


