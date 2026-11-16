package task4;

public class IndexedElement extends AbstractComponent {
    private final MathComponent base;
    private final MathComponent index;
    private static final double SCALE = 0.7;
    private static final int SHIFT_Y = 8;

    public IndexedElement(MathComponent base, MathComponent index) {
        this.base = base;
        this.index = index;
        this.base.setParent(this);
        this.index.setParent(this);
    }

    @Override
    public int getWidth() {
        return base.getWidth() + (int) Math.round(index.getWidth() * SCALE);
    }

    @Override
    public int getHeight() {
        int scaledIndexHeight = (int) Math.round(index.getHeight() * SCALE);
        return Math.max(base.getHeight(), SHIFT_Y + scaledIndexHeight);
    }

    @Override
    public String getName() {
        return "IndexedElement";
    }

    @Override
    public void location(int x, int y) {
        this.x = x;
        this.y = y;

        base.location(x, y);
        index.location(x + base.getWidth(), y + SHIFT_Y);
    }

    @Override
    public String createSvg() {
        StringBuilder svg = new StringBuilder();
        svg.append(base.createSvg());

        int targetX = x + base.getWidth();
        int targetY = y + SHIFT_Y;

        svg.append(String.format("""
                <g transform="translate(%d, %d) scale(0.7) translate(%d, %d)">
                %s</g>
                """, targetX, targetY, -targetX, -targetY, index.createSvg()));

        return svg.toString();
    }

    @Override
    public MathComponent findDeepest(int px, int py) {
        if (!contains(px, py)) {
            return null;
        }
        MathComponent target = base.findDeepest(px, py);
        if (target != null) {
            return target;
        }

        int targetX = x + base.getWidth();
        int targetY = y + SHIFT_Y;
        int localPx = targetX + (int) Math.round((px - targetX) / SCALE);
        int localPy = targetY + (int) Math.round((py - targetY) / SCALE);

        target = index.findDeepest(localPx, localPy);
        if (target != null) {
            return target;
        }

        return this;
    }

    @Override
    public void printCoordinates() {
        super.printCoordinates();
        base.printCoordinates();
        index.printCoordinates();
    }
}
