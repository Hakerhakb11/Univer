package task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class View1 implements Observer {
    private static final String FILENAME = "task2/svg/view1.svg";

    @Override
    public void update(Model model) {
        createSVG(model);
    }

    private void createSVG(Model model) {
        String svg = String.format("""
                <svg width="300" height="100" xmlns="http://www.w3.org/2000/svg">
                    <rect x="0"  y="0" width="100" height="50" fill="white" stroke="black"/>
                    <rect x="100" y="0" width="100" height="50" fill="white" stroke="black"/>
                    <rect x="200" y="0" width="100" height="50" fill="white" stroke="black"/>
                    <text x="50" y="30" font-size="16">a</text>
                    <text x="150" y="30" text-anchor="middle" font-size="16">b</text>
                    <text x="250" y="30" text-anchor="middle" font-size="16">c</text>

                    <rect x="0"  y="50" width="100" height="50" fill="white" stroke="black"/>
                    <rect x="100" y="50" width="100" height="50" fill="white" stroke="black"/>
                    <rect x="200" y="50" width="100" height="50" fill="white" stroke="black"/>
                    <text x="50" y="80" text-anchor="middle" font-size="14">%d%%</text>
                    <text x="150" y="80" text-anchor="middle" font-size="14">%d%%</text>
                    <text x="250" y="80" text-anchor="middle" font-size="14">%d%%</text>
                </svg>
                """,
                model.getA(), model.getB(), model.getC());

        try {
            Files.writeString(Path.of(FILENAME), svg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
