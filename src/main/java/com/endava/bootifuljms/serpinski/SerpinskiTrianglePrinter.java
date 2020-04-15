package com.endava.bootifuljms.serpinski;

public class SerpinskiTrianglePrinter {

    public String print(int[][] triangle) {
        StringBuilder builder = new StringBuilder();
        for (int[] row : triangle) {
            for (int j = 0; j < triangle[0].length; ++j) {
                if (isEmptyCell(row[j])) {
                    builder.append(String.format("%2s", ""));
                } else {
                    if (row[j] % 2 == 0) {
                        builder.append(String.format("%2s", ""));
                    } else {
                        builder.append(String.format("%2s", "*"));
                    }
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private boolean isEmptyCell(int i) {
        return i == 0;
    }
}
