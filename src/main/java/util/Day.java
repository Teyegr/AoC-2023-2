package util;

public abstract class Day implements Problem {
    protected final int day;
    protected final  boolean test;
    protected Day(int day, boolean test) {
        this.day = day;
        this.test = test;
    }

    public void solvePart1() {
        String[] input = InputProvider.getInput(day, test);
        long solution = solvePart1(input);
        String subject = test ? "Test": "Solution";
        String message = String.format("%s of Day %d Part 1: %d", subject, day, solution);
        System.out.println(message);
    }

    public void solvePart2() {
        String[] input = InputProvider.getInput(day, test);
        long solution = solvePart2(input);
        String subject = test ? "Test": "Solution";
        String message = String.format("%s of Day %d Part 2: %d", subject, day, solution);
        System.out.println(message);
    }
}
