package com.endava.bootifuljms.serpinski;

public class SerpinnskiTriangleCalculator {

    public SerpinskiResponse calculate(SerpinskiRequest request) {
        PascalTriangleCalculator calculator = new PascalTriangleCalculator();
        int[][] pascalTriangle = calculator.calculateForDepth(request.getDepth());
        SerpinskiTrianglePrinter trianglePrinter = new SerpinskiTrianglePrinter();
        return new SerpinskiResponse(trianglePrinter.print(pascalTriangle));
    }
}
