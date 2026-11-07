package task1;

public class Fraction implements MathComponent {
    private final MathComponent num;
    private final MathComponent den;
    private static final int PADDING = 6;
    private static final int LINE_GAP = 4;

    public Fraction(MathComponent num, MathComponent den) {
        this.num = num;
        this.den = den;
    }

    @Override
    public int getWidth() {
        return Math.max(num.getWidth(), den.getWidth()) + PADDING * 2;
    }

    @Override
    public int getHeight() {
        return num.getHeight() + den.getHeight() + LINE_GAP * 2;
    }

    @Override
    public String createSvg(int x, int y) {
        StringBuilder svg = new StringBuilder();

        int width = getWidth();
        int numX = x + (width - num.getWidth()) / 2;
        int numY = y;
        svg.append(num.createSvg(numX, numY));

        int lineY = y + num.getHeight() + LINE_GAP;
        svg.append(String.format("""
                <line x1="%d" y1="%d" x2="%d" y2="%d" stroke="white" stroke-width="2"/>
                """,
                x, lineY, x + width, lineY));

        int denX = x + (width - den.getWidth()) / 2;
        int denY = lineY + LINE_GAP;
        return svg.append(den.createSvg(denX, denY)).toString();
    }
}