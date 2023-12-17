import util.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day15 extends Day {
    protected Day15() {
        super(15, false);
        solvePart2();
    }

    @Override
    public long solvePart1(String[] input) {
        input = parseInput(input);
        long sum = 0;
        for (String content: input) {
            sum += calculateHash(content);
        }
        return sum;
    }

    @Override
    public long solvePart2(String[] input) {
        Lens[] lenses = parseInputPart2(input);
        HashMap<Integer, List<Lens>> boxes = new HashMap<>();
        processLenses(lenses, boxes);
        for (Integer i : boxes.keySet()) {
            System.out.println(i + " " + Arrays.toString(boxes.get(i).toArray()));
        }
        return calculateFocusingPower(boxes);
    }

    private String[] parseInput(String[] input) {
        return input[0].split(",");
    }

    private Lens[] parseInputPart2(String[] input) {
        input = parseInput(input);
        Lens[] lenses = new Lens[input.length];
        for (int i = 0; i < lenses.length; i++) {
            lenses[i] = parseLens(input[i]);
        }
        return lenses;
    }

    private Lens parseLens(String content) {
        StringBuilder labelBuilder = new StringBuilder();
        char operator = '0';
        int length = 0;
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '=' || c == '-') {
                operator = c;
            } else if (Character.isDigit(c)) {
                length = (int) c - 48;
            } else {
                labelBuilder.append(c);
            }
        }
        String label = labelBuilder.toString();
        int hash = calculateHash(label);
        return new Lens(label, operator, length, hash);
    }
    private int calculateHash(String content) {
        int value = 0;
        for (char c: content.toCharArray()) {
            value += c;
            value *= 17;
            value %= 256;
        }
        return value;
    }

    private void processLenses(Lens[] lenses, HashMap<Integer, List<Lens>> boxes) {
        for (Lens lens: lenses) {
            if (!boxes.containsKey(lens.hash)) {
                boxes.put(lens.hash, new ArrayList<>());
            }
            List<Lens> box = boxes.get(lens.hash);
            Lens lensInBox = box.stream()
                    .filter(l -> l.label.equals(lens.label))
                    .findAny()
                    .orElse(null);

            if (lens.operator == '=') {
                if (lensInBox == null) {
                    box.add(lens);
                } else {
                    lensInBox.length = lens.length;
                }
            } else {
                if (lensInBox != null) {
                    box.remove(lensInBox);
                }
            }
        }
    }
    private long calculateFocusingPower(HashMap<Integer, List<Lens>> boxes) {
        long sum = 0;
        for (Integer i: boxes.keySet()) {
            for (int j = 0; j < boxes.get(i).size(); j++) {
                sum += (long) (i + 1) * (j + 1) * boxes.get(i).get(j).length;
            }
        }
        return sum;
    }
    static class Lens {
        String label;
        char operator;
        int length;
        int hash;

        public Lens(String label, char operator, int length, int hash) {
            this.label = label;
            this.operator = operator;
            this.length = length;
            this.hash = hash;
        }

        public String toString() {
            return label + " " + length;
        }
    }
}
