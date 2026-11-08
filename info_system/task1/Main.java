package task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

	public static void renderSvg(MathComponent component, String filePath) throws IOException {
		int svgWidth = component.getWidth();
		int svgHeight = component.getHeight();

		String svg = component.createSvg(0, 0);

		String fullSvg = String.format("""
				<svg xmlns="http://www.w3.org/2000/svg" width="%d" height="%d">\n%s</svg>
				""",
				svgWidth, svgHeight, svg);

		Files.writeString(Path.of("task1/svg", filePath), fullSvg);
	}

	public static void main(String[] args) throws Exception {
		MathComponent A = new Leaf("a");
		MathComponent B = new Leaf("b");
		MathComponent formula = new Fraction(A, B);

		MathComponent numIndexed = new IndexedElement(B, formula);

		MathComponent[][] arr = {
				{ A, formula, B },
				{ formula, B, A }
		};
		MathComponent matrix = new Matrix(arr);
		MathComponent indexedIntegral = new Integral(B);

		MathComponent numIndexed2 = new IndexedElement(indexedIntegral, matrix);

		MathComponent[][] arr2 = {
				{ numIndexed, formula, B },
				{ formula, numIndexed2, A }
		};
		MathComponent matrix2 = new Matrix(arr2);

		MathComponent indexed = new IndexedElement(B, matrix2);

		MathComponent integral = new Integral(B);

		renderSvg(formula, "formula.svg");
		renderSvg(A, "symbolA.svg");
		renderSvg(B, "symbolB.svg");

		renderSvg(numIndexed, "numIndexed.svg");

		renderSvg(matrix, "matrix.svg");
		renderSvg(numIndexed2, "numIndexed2.svg");
		renderSvg(matrix2, "matrix2.svg");

		renderSvg(indexed, "Indexed.svg");

		renderSvg(integral, "integral.svg");
	}
}
