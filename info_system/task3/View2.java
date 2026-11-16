package task3;

public class View2 implements ViewElement {
    @Override
    public int getWidth() {
        return 300;
    }

    @Override
    public int getHeight() {
        return 250;
    }

    @Override
    public String getFileName() {
        return "view2.svg";
    }

    @Override
    public String createSvg(Model model) {
        int a = model.getA();
        int b = model.getB();
        int c = model.getC();

        int hA = a * 2;
        int yA = 220 - hA;

        int hB = b * 2;
        int yB = 220 - hB;

        int hC = c * 2;
        int yC = 220 - hC;

        String svg = String.format("""
                <svg width="300" height="250" xmlns="http://www.w3.org/2000/svg">
                    <line x1="35" y1="220" x2="290" y2="220" stroke="white" stroke-width="2"/>
                    <line x1="35" y1="15"  x2="35"  y2="220" stroke="white" stroke-width="2"/>

                    <text x="35" y="220" fill="white" font-size="10" text-anchor="end">0%%</text>
                    <text x="35" y="170" fill="white" font-size="10" text-anchor="end">25%%</text>
                    <text x="35" y="120" fill="white" font-size="10" text-anchor="end">50%%</text>
                    <text x="35" y="70"  fill="white" font-size="10" text-anchor="end">75%%</text>
                    <text x="35" y="20"  fill="white" font-size="10" text-anchor="end">100%%</text>

                    <rect x="55" y="%d" width="60" height="%d" fill="#66CCFF" stroke="white"/>
                    <text x="85" y="240" fill="white" text-anchor="middle" font-size="12">a</text>
                    <text x="85" y="%d" fill="white" text-anchor="middle" font-size="10">%d%%</text>

                    <rect x="135" y="%d" width="60" height="%d" fill="#66FF66" stroke="white"/>
                    <text x="165" y="240" fill="white" text-anchor="middle" font-size="12">b</text>
                    <text x="165" y="%d" fill="white" text-anchor="middle" font-size="10">%d%%</text>

                    <rect x="215" y="%d" width="60" height="%d" fill="#FF6666" stroke="white"/>
                    <text x="245" y="240" fill="white" text-anchor="middle" font-size="12">c</text>
                    <text x="245" y="%d" fill="white" text-anchor="middle" font-size="10">%d%%</text>
                </svg>
                """,
                yA, hA, yA, a,
                yB, hB, yB, b,
                yC, hC, yC, c);

        return svg;
    }

}
