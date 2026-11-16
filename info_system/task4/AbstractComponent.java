package task4;

public abstract class AbstractComponent implements MathComponent {
    protected MathComponent parent;
    protected int x, y;

    @Override
    public MathComponent getParent() {
        return parent;
    }

    @Override
    public void setParent(MathComponent parent) {
        this.parent = parent;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean contains(int px, int py) {
        return px >= x && px <= x + getWidth() && py >= y && py <= y + getHeight();
    }

    @Override
    public void handleRequest(int px, int py, RequestType type) {
        switch (type) {
            case PRINT:
                handlePrint(px, py);
                break;
            case HELP:
                handleHelp(px, py);
                break;
        }
    }

    @Override
    public void handleHelp(int px, int py) {
        if (contains(px, py)) {
            System.out.println("я " + getName() + " с шириной " + getWidth() + " и высотой " + getHeight());
        }
        if (parent != null) {
            System.out.println(getName() + " !Передача родителю (" + parent.getName() + ")");
            parent.handleHelp(px, py);
        } else {
            System.out.println("!!Цепочка печати завершена на корне: " + getName());
        }

    }

    @Override
    public void handlePrint(int px, int py) {
        if (contains(px, py)) {
            String filename = "task4/svg/" + getName() + "_" + x + "_" + y + ".svg";
            int oldX = x;
            int oldY = y;
            location(10, 10);
            String content = createSvg();
            location(oldX, oldY);
            SVGWriter.save(content, filename, 10 + getWidth(), 10 + getHeight());
            return;
        }
        if (parent != null) {
            System.out.println(getName() + " !Передача родителю (" + parent.getName() + ")");
            parent.handlePrint(px, py);
        } else {
            System.out.println("!!Цепочка печати завершена на корне: " + getName());
        }
    }

    @Override
    public MathComponent findDeepest(int px, int py) {
        if (contains(px, py)) {
            return this;
        }
        return null;
    }

    @Override
    public void printCoordinates() {
        System.out.println(getName() + " X: " + x + "-" + (x + getWidth()) + ", Y: " + y + "-" + (y + getHeight()));
    }
}
