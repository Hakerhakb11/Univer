package task3;

public class View3 implements ViewElement {
    @Override
    public int getWidth() {
        return 300;
    }

    @Override
    public int getHeight() {
        return 300;
    }

    @Override
    public String getFileName() {
        return "view3.svg";
    }

    @Override
    public String createSvg(Model model) {
        int a = model.getA();
        int b = model.getB();
        int c = model.getC();

        String svg = String.format("""
                <svg width="300" height="300" xmlns="http://www.w3.org/2000/svg">%s%s%s
                </svg>
                """,
                drawSlice(0, a, "a", "#66CCFF"),
                drawSlice(a, b, "b", "#66FF66"),
                drawSlice(a + b, c, "c", "#FF6666"));
        return svg;
    }

    private String drawSlice(int startPercent, int percent, String label, String color) {
        if (percent == 0) {
            return "";
        }

        if (percent == 100) {
            return String.format(
                    """
                            <circle cx="150" cy="150" r="145" fill="%s" stroke="white" stroke-width="2"/>
                            <text x="150" y="150" text-anchor="middle" dominant-baseline="central" font-size="14" fill="black">%s (100%%)</text>
                            """,
                    color, label);
        }

        int cx = 150, cy = 150, r = 150;

        double startAngle = startPercent * 3.6;
        double endAngle = (startPercent + percent) * 3.6;

        int x1 = (int) (cx + r * Math.cos(Math.toRadians(startAngle)));
        int y1 = (int) (cy + r * Math.sin(Math.toRadians(startAngle)));
        int x2 = (int) (cx + r * Math.cos(Math.toRadians(endAngle)));
        int y2 = (int) (cy + r * Math.sin(Math.toRadians(endAngle)));

        double midAngle = (startAngle + endAngle) / 2.0;
        int tx = (int) (cx + (r * 0.6) * Math.cos(Math.toRadians(midAngle)));
        int ty = (int) (cy + (r * 0.6) * Math.sin(Math.toRadians(midAngle)));

        int largeArc;
        if (percent > 50) {
            largeArc = 1;
        } else {
            largeArc = 0;
        }

        return String.format(
                """
                        <path d="M %d,%d L %d,%d A %d,%d 0 %d 1 %d,%d Z" fill="%s" stroke="white" stroke-width="1.5"/>
                        <text x="%d" y="%d" text-anchor="middle" dominant-baseline="central" font-size="12" fill="black">%s (%d%%)</text>
                        """,
                cx, cy, x1, y1, r, r, largeArc, x2, y2, color,
                tx, ty, label, percent);
    }

}
