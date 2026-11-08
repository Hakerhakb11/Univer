package task1;

public class Leaf implements MathComponent {
    private final String text;
    private static final int CHAR_WIDTH = 12;
    private static final int HEIGHT = 20;

    public Leaf(String text) {
        this.text = text;
    }

    @Override
    public int getWidth() {
        return text.length() * CHAR_WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public String createSvg(int x, int y) {
        return String.format(
                """
                        <text x="%d" y="%d" font-family="monospace" font-size="20" fill="white" dominant-baseline="hanging">%s</text>
                        """,
                x, y, text);
    }
}
