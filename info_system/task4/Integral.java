package task4;

public class Integral extends AbstractComponent {
    private final MathComponent body;
    private final MathComponent diff;
    private static final int SIGN_HEIGHT = 33;
    private static final int SIGN_WIDTH = 14;
    private static final int GAP = 4;

    public Integral(MathComponent body, MathComponent diff) {
        this.body = body;
        this.diff = diff;
        this.body.setParent(this);
        this.diff.setParent(this);
    }

    public Integral(MathComponent body) {
        this(body, new Leaf("dx"));
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
    public String getName() {
        return "Integral";
    }

    @Override
    public void location(int x, int y) {
        this.x = x;
        this.y = y;

        int bodyX = x + SIGN_WIDTH + GAP;
        int bodyY = y + (getHeight() - body.getHeight()) / 2;
        body.location(bodyX, bodyY);

        int diffX = bodyX + body.getWidth() + GAP;
        int diffY = y + (getHeight() - diff.getHeight()) / 2;
        diff.location(diffX, diffY);
    }

    @Override
    public String createSvg() {
        StringBuilder svg = new StringBuilder();

        int signY = y + (getHeight() + SIGN_HEIGHT) / 2;

        svg.append(String.format("""
                <text x="%d" y="%d" font-family="serif" font-size="30" fill="white">∫</text>
                """, x, signY - 4));

        svg.append(body.createSvg());

        svg.append(diff.createSvg());

        return svg.toString();
    }

    @Override
    public MathComponent findDeepest(int px, int py) {
        if (!contains(px, py)) {
            return null;
        }
        MathComponent target = body.findDeepest(px, py);
        if (target != null)
            return target;

        target = diff.findDeepest(px, py);
        if (target != null)
            return target;

        return this;
    }

    @Override
    public void printCoordinates() {
        super.printCoordinates();
        body.printCoordinates();
        diff.printCoordinates();
    }

}
