package task4;

public interface MathComponent {
    int getWidth();

    int getHeight();

    void setParent(MathComponent parent);

    MathComponent getParent();

    int getX();

    int getY();

    String getName();

    void location(int x, int y);

    String createSvg();

    boolean contains(int px, int py);

    void handleRequest(int px, int py, RequestType type);

    void handleHelp(int px, int py);

    void handlePrint(int px, int py);

    MathComponent findDeepest(int px, int py);

    void printCoordinates();
}
