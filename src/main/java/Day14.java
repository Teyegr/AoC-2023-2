import util.Day;

import java.util.Arrays;
import java.util.HashMap;

public class Day14 extends Day {
    public Day14() {
        super(14, false);
    }

    @Override
    public long solvePart1(String[] input) {
        char[][] grid = inputToCharArray(input);
        tiltNorth(grid);
        return calculateLoad(grid);
    }

    @Override
    public long solvePart2(String[] input) {
        char[][] grid = inputToCharArray(input);
        cycle(grid, 1000000000);
        return calculateLoad(grid);
    }

    private void cycle(char[][] grid) {
        tiltNorth(grid);
        tiltWest(grid);
        tiltSouth(grid);
        tiltEast(grid);
    }
    private void cycle(char[][] grid, int count) {
        HashMap<Integer, Integer> memo = new HashMap<>();

        for (int i = 0; i < count; i++) {
            if (memo.containsKey(Arrays.deepHashCode(grid))) {
                int jump = i - memo.get(Arrays.deepHashCode(grid));
                int remaining = count - i;
                count = remaining % jump + i;
            }
            memo.put(Arrays.deepHashCode(grid), i);
            cycle(grid);
        }
    }
    private void tiltNorth(char[][] grid) {
        int[] move = new int[grid[0].length];
        Arrays.fill(move, 0);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                char c = grid[i][j];

                if (c == '.') {
                    move[j] = move[j] + 1;
                } else if (c == 'O') {
                    swap(grid, i, j, i - move[j], j);
                } else if (c == '#') {
                    move[j] = 0;
                } else {
                    throw new RuntimeException("Invalid symbol in input: " + c);
                }
            }
        }
    }

    private void tiltSouth(char[][] grid) {
        int[] move = new int[grid[0].length];
        Arrays.fill(move, 0);
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = 0; j < grid[0].length; j++) {
                char c = grid[i][j];

                if (c == '.') {
                    move[j] = move[j] + 1;
                } else if (c == 'O') {
                    swap(grid, i, j, i + move[j], j);
                } else if (c == '#') {
                    move[j] = 0;
                } else {
                    throw new RuntimeException("Invalid symbol in input: " + c);
                }
            }
        }
    }

    private void tiltWest(char[][] grid) {
        int[] move = new int[grid.length];
        Arrays.fill(move, 0);
        for (int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                char c = grid[i][j];

                if (c == '.') {
                    move[i] = move[i] + 1;
                } else if (c == 'O') {
                    swap(grid, i, j, i, j - move[i]);
                } else if (c == '#') {
                    move[i] = 0;
                } else {
                    throw new RuntimeException("Invalid symbol in input: " + c);
                }
            }
        }
    }

    private void tiltEast(char[][] grid) {
        int[] move = new int[grid.length];
        Arrays.fill(move, 0);
        for (int j = grid[0].length - 1; j >= 0; j--) {
            for (int i = 0; i < grid.length; i++) {
                char c = grid[i][j];

                if (c == '.') {
                    move[i] = move[i] + 1;
                } else if (c == 'O') {
                    swap(grid, i, j, i, j + move[i]);
                } else if (c == '#') {
                    move[i] = 0;
                } else {
                    throw new RuntimeException("Invalid symbol in input: " + c);
                }
            }
        }
    }

    private void swap(char[][] grid, int i1, int j1, int i2, int j2) {
        char temp = grid[i1][j1];
        grid[i1][j1] = grid[i2][j2];
        grid[i2][j2] = temp;
    }

    private void logGrid(char[][] grid) {
        for (char[] row: grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    private char[][] inputToCharArray(String[] input) {
        char[][] grid = new char[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++) {
            grid[i] = input[i].toCharArray();
        }
        return grid;
    }

    private long calculateLoad(char[][] grid) {
        long sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'O') {
                    sum += grid.length - i;
                }
            }
        }
        return sum;
    }

}
