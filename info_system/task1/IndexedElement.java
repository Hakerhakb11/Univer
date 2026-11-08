package task1;

public class IndexedElement implements MathComponent {
    private final MathComponent base;
    private final MathComponent index;
    private static final double SCALE = 0.7;
    private static final int SHIFT_Y = 8;

    public IndexedElement(MathComponent base, MathComponent index) {
        this.base = base;
        this.index = index;
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
    public String createSvg(int x, int y) {
        StringBuilder svg = new StringBuilder();

        svg.append(base.createSvg(x, y));

        int targetX = x + base.getWidth();
        int targetY = y + SHIFT_Y;

        int scaledX = (int) Math.round(targetX / SCALE);
        int scaledY = (int) Math.round(targetY / SCALE);

        svg.append(String.format("""
                <g transform="scale(0.7)">
                %s</g>
                """, index.createSvg(scaledX, scaledY)));

        return svg.toString();
    }
}
