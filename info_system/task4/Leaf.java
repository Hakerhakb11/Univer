package task4;

public class Leaf extends AbstractComponent {
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
    public String getName() {
        return "Leaf_" + text;
    }

    @Override
    public void location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String createSvg() {
        return String.format(
                """
                        <text x="%d" y="%d" font-family="monospace" font-size="20" fill="white" dominant-baseline="hanging">%s</text>
                        """,
                x, y, text);
    }

    @Override
    public void handlePrint(int px, int py) {
        if (parent != null) {
            System.out.println(getName() + " !Передача родителю (" + parent.getName() + ")");
            parent.handlePrint(px, py);
        } else {
            System.out.println("!!Цепочка завершена на: " + getName());
        }
    }
}
