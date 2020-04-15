package com.endava.bootifuljms.serpinski;

public class PascalTriangleCalculator {
    public int[][] calculateForDepth(int depth) {
        if (depth < 1) {
            return new int[0][];
        } else if (depth == 1) {
            return new int[][]{{1}};
        }
        int columns = depth * 2 - 1;
        int[][] triangle = new int[depth][columns];
        int middle = columns / 2;
        triangle[0][middle] = 1;
        for (int i = 1; i < triangle.length; ++i) {
            for (int j = 0; j < triangle[0].length; ++j) {
                if (j == (middle - i) || j == (middle + i)) {
                    triangle[i][j] = 1;
                } else if ((j - 1 > 0) && (j + 1 < columns)) {
                    int left = triangle[i - 1][j - 1];
                    int right = triangle[i - 1][j + 1];
                    if (left != 0 && right != 0) {
                        triangle[i][j] = left + right;
                    }
                }
            }
        }
        return triangle;
    }
}
