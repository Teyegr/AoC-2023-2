package util;

public class DayRunner {
    private final int day;
    private final boolean test;
    protected final String[] input;
    private final Day dayImplementations;

    public DayRunner(int day, boolean test, Day dayImplementations) {
        this.day = day;
        this.test = test;
        this.dayImplementations = dayImplementations;
        this.input = InputProvider.getInput(day, test);
    }

    public void runPartOne() {
        long result = dayImplementations.partOne(input);
        String subject = test ? "Test": "Day";
        String part = "Part 1";
        String message = String.format("The solution of %s %d %s: %d", subject, day, part, result);
        System.out.println(message);
    }

    public void runPartTwo() {
        long result = dayImplementations.partTwo(input);
        String subject = test ? "Test": "Day";
        String part = "Part 2";
        String message = String.format("The solution of %s %d %s: %d", subject, day, part, result);
        System.out.println(message);
    }

    public void run() {
        runPartOne();
        runPartTwo();
    }
}
