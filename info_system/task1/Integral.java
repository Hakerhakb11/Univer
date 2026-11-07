package task1;

public class Integral implements MathComponent {
    private final MathComponent body;
    private final MathComponent diff;
    private static final int SIGN_HEIGHT = 33;
    private static final int SIGN_WIDTH = 14;
    private static final int GAP = 4;

    public Integral(MathComponent body, MathComponent diff) {
        this.body = body;
        this.diff = diff;
    }

    public Integral(MathComponent body) {
        this(body, new Symbol("dx"));
    }

    @Override
    public int getWidth() {
        return SIGN_WIDTH + GAP + body.getWidth() + GAP + diff.getWidth();
    }

    @Override
    public int getHeight() {
        return Math.max(body.getHeight(), SIGN_HEIGHT);
    }

    @Override
    public String createSvg(int x, int y) {
        StringBuilder svg = new StringBuilder();
        int totalHeight = getHeight();

        int signY = y + (totalHeight + SIGN_HEIGHT) / 2;

        svg.append(String.format("""
                <text x="%d" y="%d" font-family="serif" font-size="30" fill="white">∫</text>
                """, x, signY - 4));

        int bodyX = x + SIGN_WIDTH + GAP;
        int bodyY = y + (totalHeight - body.getHeight()) / 2;
        svg.append(body.createSvg(bodyX, bodyY));

        int diffX = bodyX + body.getWidth() + GAP;
        int diffY = y + (totalHeight - diff.getHeight()) / 2;
        svg.append(diff.createSvg(diffX, diffY));

        return svg.toString();
    }
}