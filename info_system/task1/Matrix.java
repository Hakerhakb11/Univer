package task1;

public class Matrix implements MathComponent {
    private final MathComponent[][] matrix;
    private static final int COL_GAP = 12;
    private static final int ROW_GAP = 8;
    private static final int BRACKET_WIDTH = 8;
    private static final int PADDING = 6;

    public Matrix(MathComponent[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Матрица не может быть пустой");
        }
        this.matrix = matrix;
    }

    private int getRows() {
        return matrix.length;
    }

    private int getCols() {
        return matrix[0].length;
    }

    private int[] getColumnWidths() {
        int n = getCols();
        int[] colWidths = new int[n];
        for (int i = 0; i < n; i++) {
            int maxWidth = 0;
            for (int j = 0; j < getRows(); j++) {
                maxWidth = Math.max(maxWidth, matrix[j][i].getWidth());
            }
            colWidths[i] = maxWidth;
        }
        return colWidths;
    }

    private int getRowHeights()[] {
        int n = getRows();
        int[] rowHeights = new int[n];
        for (int i = 0; i < n; i++) {
            int maxHeight = 0;
            for (int j = 0; j < getCols(); j++) {
                maxHeight = Math.max(maxHeight, matrix[i][j].getHeight());
            }
            rowHeights[i] = maxHeight;
        }
        return rowHeights;
    }

    @Override
    public int getWidth() {
        int[] colWidths = getColumnWidths();
        int fullWidth = 0;
        for (int width : colWidths) {
            fullWidth += width;
        }
        fullWidth += (getCols() - 1) * COL_GAP;
        return fullWidth + (BRACKET_WIDTH + PADDING) * 2;
    }

    @Override
    public int getHeight() {
        int[] rowHeights = getRowHeights();
        int fullHeight = 0;
        for (int height : rowHeights) {
            fullHeight += height;
        }
        fullHeight += (getRows() - 1) * ROW_GAP;
        return fullHeight + PADDING * 2;
    }

    @Override
    public String createSvg(int x, int y) {
        StringBuilder svg = new StringBuilder();

        int fullWidth = getWidth();
        int fullHeight = getHeight();
        int[] colWidths = getColumnWidths();
        int[] rowHeights = getRowHeights();

        int midY = y + fullHeight / 2;
        svg.append(String.format("""
                <path d="M %d %d Q %d %d %d %d" stroke="white" fill="none" stroke-width="2"/>
                """,
                x + BRACKET_WIDTH, y,
                x - BRACKET_WIDTH, midY,
                x + BRACKET_WIDTH, y + fullHeight));

        int rightX = x + fullWidth;
        int rightBrX = rightX - BRACKET_WIDTH;
        svg.append(String.format("""
                <path d="M %d %d Q %d %d %d %d" stroke="white" fill="none" stroke-width="2"/>
                """,
                rightBrX, y,
                rightX + BRACKET_WIDTH, midY,
                rightBrX, y + fullHeight));

        int startX = x + BRACKET_WIDTH + PADDING;
        int currentY = y + PADDING;

        for (int i = 0; i < getRows(); i++) {
            int currentX = startX;
            for (int j = 0; j < getCols(); j++) {
                MathComponent obj = matrix[i][j];

                int cellX = currentX + (colWidths[j] - obj.getWidth()) / 2;
                int cellY = currentY + (rowHeights[i] - obj.getHeight()) / 2;

                svg.append(obj.createSvg(cellX, cellY));
                currentX += colWidths[j] + COL_GAP;
            }
            currentY += rowHeights[i] + ROW_GAP;
        }

        return svg.toString();
    }
}
