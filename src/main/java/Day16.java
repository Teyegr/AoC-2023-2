import util.Day;

import java.util.HashMap;
import java.util.Stack;

public class Day16 extends Day {
    protected Day16() {
        super(16, false);
        solvePart1();
        solvePart2();
    }

    @Override
    public long solvePart1(String[] input) {
        char[][] grid = parseInput(input);
        HashMap<Direction, boolean[][]> visit = depthFirstSearchStack(grid);
        boolean[][] visited = translate(visit);
        return countEnergy(visited);
    }

    @Override
    public long solvePart2(String[] input) {
        char[][] grid = parseInput(input);
        return findMaximumEnergy(grid);
    }

    private char[][] parseInput(String[] input) {
        char[][] grid = new char[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++) {
            grid[i] = input[i].toCharArray();
        }
        return grid;
    }

    private HashMap<Direction, boolean[][]> newMap(int r, int c) {
        HashMap<Direction, boolean[][]> visit = new HashMap<>();
        visit.put(Direction.UP, new boolean[r][c]);
        visit.put(Direction.DOWN, new boolean[r][c]);
        visit.put(Direction.RIGHT, new boolean[r][c]);
        visit.put(Direction.LEFT, new boolean[r][c]);
        return visit;
    }
    private HashMap<Direction, boolean[][]> depthFirstSearch(char[][] grid) {
        HashMap<Direction, boolean[][]> visit = newMap(grid.length, grid[0].length);
        depthFirstSearch(0, 0, Direction.RIGHT, grid, visit);
        return visit;
    }
    private void depthFirstSearch(int i, int j, Direction direction, char[][] grid, HashMap<Direction, boolean[][]> visit) {
        while (!(i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) && !(visit.get(direction)[i][j])) {
            visit.get(direction)[i][j] = true;

            switch (grid[i][j]) {
                case '.':
                    switch (direction) {
                        case UP:
                            i--;break;
                        case DOWN:
                            i++;break;
                        case RIGHT:
                            j++;break;
                        case LEFT:
                            j--;break;
                    }
                    break;
                case '|':
                    switch (direction) {
                        case UP:
                            i--;break;
                        case DOWN:
                            i++;break;
                        default:
                            depthFirstSearch(i - 1, j, Direction.UP, grid, visit);
                            i++;
                            direction = Direction.DOWN;
                            break;
                    }
                    break;
                case '-':
                    switch (direction) {
                        case RIGHT:
                            j++;break;
                        case LEFT:
                            j--;break;
                        default:
                            depthFirstSearch(i, j - 1, Direction.LEFT, grid, visit);
                            j++;
                            direction = Direction.RIGHT;
                            break;
                    }
                    break;
                case '/':
                    switch (direction) {
                        case UP:
                            j++;
                            direction = Direction.RIGHT;
                            break;
                        case DOWN:
                            j--;
                            direction = Direction.LEFT;
                            break;
                        case RIGHT:
                            i--;
                            direction = Direction.UP;
                            break;
                        case LEFT:
                            i++;
                            direction = Direction.DOWN;
                            break;
                    }
                    break;
                case '\\':
                    switch (direction) {
                        case UP:
                            j--;
                            direction = Direction.LEFT;
                            break;
                        case DOWN:
                            j++;
                            direction = Direction.RIGHT;
                            break;
                        case RIGHT:
                            i++;
                            direction = Direction.DOWN;
                            break;
                        case LEFT:
                            i--;
                            direction = Direction.UP;
                            break;
                    }
            }
        }
    }

    private HashMap<Direction, boolean[][]> depthFirstSearchStack(char[][] grid) {
        return depthFirstSearchStack(new Laser(0, 0, Direction.RIGHT), grid, newMap(grid.length, grid[0].length));
    }
    private HashMap<Direction, boolean[][]> depthFirstSearchStack(Laser laser, char[][] grid, HashMap<Direction, boolean[][]> visited) {
        Stack<Laser> laserStack = new Stack<>();
        laserStack.add(laser);
        while (!laserStack.isEmpty()) {
            laser = laserStack.pop();
            if (laser.i < 0 || laser.j < 0 || laser.i >= grid.length || laser.j >= grid[0].length) {
                continue;
            }

            if (visited.get(laser.direction)[laser.i][laser.j]) {
                continue;
            }

            visited.get(laser.direction)[laser.i][laser.j] = true;

            int i = laser.i;
            int j = laser.j;
            Direction direction = laser.direction;
            switch (grid[i][j]) {
                case '.':
                    switch (direction) {
                        case UP:
                            i--;break;
                        case DOWN:
                            i++;break;
                        case RIGHT:
                            j++;break;
                        case LEFT:
                            j--;break;
                    }
                    break;
                case '|':
                    switch (direction) {
                        case UP:
                            i--;break;
                        case DOWN:
                            i++;break;
                        default:
                            laserStack.add(new Laser(i - 1, j, Direction.UP));
                            i++;
                            direction = Direction.DOWN;
                            break;
                    }
                    break;
                case '-':
                    switch (direction) {
                        case RIGHT:
                            j++;break;
                        case LEFT:
                            j--;break;
                        default:
                            laserStack.add(new Laser(i, j - 1, Direction.LEFT));
                            j++;
                            direction = Direction.RIGHT;
                            break;
                    }
                    break;
                case '/':
                    switch (direction) {
                        case UP:
                            j++;
                            direction = Direction.RIGHT;
                            break;
                        case DOWN:
                            j--;
                            direction = Direction.LEFT;
                            break;
                        case RIGHT:
                            i--;
                            direction = Direction.UP;
                            break;
                        case LEFT:
                            i++;
                            direction = Direction.DOWN;
                            break;
                    }
                    break;
                case '\\':
                    switch (direction) {
                        case UP:
                            j--;
                            direction = Direction.LEFT;
                            break;
                        case DOWN:
                            j++;
                            direction = Direction.RIGHT;
                            break;
                        case RIGHT:
                            i++;
                            direction = Direction.DOWN;
                            break;
                        case LEFT:
                            i--;
                            direction = Direction.UP;
                            break;
                    }
            }
            laserStack.add(new Laser(i, j , direction));
        }
        return visited;
    }

    private boolean[][] translate(HashMap<Direction, boolean[][]> visit) {
        boolean[][] visited = new boolean[visit.get(Direction.UP).length][visit.get(Direction.UP)[0].length];
        for (int i = 0; i < visit.get(Direction.UP).length; i++) {
            for (int j = 0; j < visit.get(Direction.UP)[0].length; j++) {
                visited[i][j] = (visit.get(Direction.UP)[i][j] || visit.get(Direction.DOWN)[i][j] ||
                                 visit.get(Direction.RIGHT)[i][j] || visit.get(Direction.LEFT)[i][j]);
            }
        }
        return visited;
    }

    private long countEnergy(boolean[][] visited) {
        long sum = 0;
        for (boolean[] row: visited) {
            for (boolean visit: row) {
                if (visit) {
                    sum++;
                }
            }
        }
        return sum;
    }
    private void visualize(boolean[][] visited) {
        for (boolean[] row: visited) {
            for (boolean visit: row) {
                System.out.print(visit ? '#' : '.');
            }
            System.out.println();
        }
    }

    private long findMaximumEnergy(char[][] grid) {
        long maximumEnergy = 0;
        for (int i = 0; i < grid[0].length; i++) {
            HashMap<Direction, boolean[][]> visit = newMap(grid.length, grid[0].length);
            depthFirstSearchStack(new Laser(0, i, Direction.DOWN), grid, visit);
            long energy = countEnergy(translate(visit));
            if (energy > maximumEnergy) {
                maximumEnergy = energy;
            }
        }
        for (int i = 0; i < grid[0].length; i++) {
            HashMap<Direction, boolean[][]> visit = newMap(grid.length, grid[0].length);
            depthFirstSearchStack(new Laser(grid.length - 1, i, Direction.UP), grid, visit);
            long energy = countEnergy(translate(visit));
            if (energy > maximumEnergy) {
                maximumEnergy = energy;
            }
        }
        for (int i = 0; i < grid.length; i++) {
            HashMap<Direction, boolean[][]> visit = newMap(grid.length, grid[0].length);
            depthFirstSearchStack(new Laser(i, 0, Direction.RIGHT), grid, visit);
            long energy = countEnergy(translate(visit));
            if (energy > maximumEnergy) {
                maximumEnergy = energy;
            }
        }
        for (int i = 0; i < grid[0].length; i++) {
            HashMap<Direction, boolean[][]> visit = newMap(grid.length, grid[0].length);
            depthFirstSearchStack(new Laser(i, grid[0].length - 1, Direction.LEFT), grid, visit);
            long energy = countEnergy(translate(visit));
            if (energy > maximumEnergy) {
                maximumEnergy = energy;
            }
        }
        return maximumEnergy;
    }
    static enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    static class Laser {
        int i;
        int j;
        Direction direction;

        public Laser(int i, int j, Direction direction) {
            this.i = i;
            this.j = j;
            this.direction = direction;
        }
    }
}
