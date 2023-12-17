package util;

import java.io.*;

public class InputProvider {
    private static String get(int day, boolean test) {
        StringBuilder builder = new StringBuilder();
        String baseFolder = "src\\main\\resources";
        String subFolder = test ? "test" : "day";
        String filename = day > 9 ? day + ".txt" : "0" + day + ".txt";
        File file = new File(baseFolder, subFolder);
        file = new File(file, filename);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    public static String[] getInput(int day, boolean test) {
        return get(day, test).split("\n");
    }
}
