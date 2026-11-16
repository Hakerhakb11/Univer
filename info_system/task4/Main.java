package task4;

public class Main {

	public static void simulateClick(MathComponent root, int px, int py, RequestType type) {
		System.out.printf("Клик (%d, %d) Запрос: %s\n", px, py, type);

		MathComponent target = root.findDeepest(px, py);

		if (target != null) {
			target.handleRequest(px, py, type);
		} else {
			System.out.println("Клик мимо:");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		MathComponent fraction = new Fraction(new Leaf("a"), new Leaf("b"));
		MathComponent integral = new Integral(fraction);
		MathComponent indexed = new IndexedElement(new Leaf("x"), new Leaf("2"));
		MathComponent[][] arr1 = {
				{ integral, indexed }
		};
		MathComponent matrix1 = new Matrix(arr1);

		MathComponent formula = new Fraction(new Leaf("a"), new Leaf("b"));
		MathComponent numIndexed = new IndexedElement(new Leaf("67"), formula);
		MathComponent integral2 = new Integral(new Leaf("B"));
		MathComponent numIndexed2 = new IndexedElement(new Leaf("n"), integral2);
		MathComponent[][] arr2 = {
				{ new Leaf("A"), numIndexed, new Leaf("B") },
				{ numIndexed2, new Leaf("B"), new Leaf("A") }
		};
		MathComponent matrix2 = new Matrix(arr2);

		matrix1.location(20, 20);
		matrix2.location(20, 40 + matrix1.getHeight());

		String svg = matrix1.createSvg() + matrix2.createSvg();

		int fullWidth = 20 + matrix2.getWidth();
		int fullHeight = 60 + matrix1.getHeight() + matrix2.getHeight();

		SVGWriter.save(svg, "task4/svg/fullSvg.svg", fullWidth, fullHeight);
		System.out.println("Координаты элементов:");
		matrix1.printCoordinates();
		matrix2.printCoordinates();

		System.out.println("---------------------------------\n");
		simulateClick(matrix1, 62, 35, RequestType.HELP);
		simulateClick(matrix1, 62, 50, RequestType.PRINT);
		simulateClick(matrix1, 38, 45, RequestType.PRINT);
		simulateClick(matrix1, 131, 52, RequestType.HELP);
		simulateClick(matrix1, 500, 500, RequestType.HELP);

		simulateClick(matrix2, 60, 127, RequestType.HELP);

		simulateClick(matrix2, 110, 115, RequestType.HELP);

		simulateClick(matrix2, 131, 120, RequestType.HELP);

		simulateClick(matrix2, 131, 131, RequestType.PRINT);

		simulateClick(matrix2, 50, 175, RequestType.PRINT);

		simulateClick(matrix2, 78, 175, RequestType.HELP);

		simulateClick(matrix2, 90, 150, RequestType.PRINT);

		simulateClick(matrix1, 62, 35, RequestType.PRINT);

	}
}
