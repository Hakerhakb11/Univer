package task4;

public class Fraction extends AbstractComponent {
    private final MathComponent num;
    private final MathComponent den;
    private static final int PADDING = 6;
    private static final int LINE_GAP = 4;

    public Fraction(MathComponent num, MathComponent den) {
        this.num = num;
        this.den = den;
        this.num.setParent(this);
        this.den.setParent(this);
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
    public String getName() {
        return "Fraction";
    }

    @Override
    public void location(int x, int y) {
        this.x = x;
        this.y = y;

        int numX = x + (getWidth() - num.getWidth()) / 2;
        num.location(numX, y);

        int lineY = y + num.getHeight() + LINE_GAP;
        int denX = x + (getWidth() - den.getWidth()) / 2;
        int denY = lineY + LINE_GAP;
        den.location(denX, denY);
    }

    @Override
    public String createSvg() {
        StringBuilder svg = new StringBuilder();

        svg.append(num.createSvg());

        int lineY = y + num.getHeight() + LINE_GAP;
        svg.append(String.format("""
                <line x1="%d" y1="%d" x2="%d" y2="%d" stroke="white" stroke-width="2"/>
                """,
                x, lineY, x + getWidth(), lineY));

        return svg.append(den.createSvg()).toString();
    }

    @Override
    public MathComponent findDeepest(int px, int py) {
        if (!contains(px, py)) {
            return null;
        }
        MathComponent target = num.findDeepest(px, py);
        if (target != null)
            return target;

        target = den.findDeepest(px, py);
        if (target != null)
            return target;

        return this;
    }

    @Override
    public void printCoordinates() {
        super.printCoordinates();
        num.printCoordinates();
        den.printCoordinates();
    }

}
