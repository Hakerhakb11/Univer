package task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface ViewElement extends Observer {
    int getWidth();

    int getHeight();

    String getFileName();

    String createSvg(Model model);

    @Override
    default void update(Model model) {
        String fullSvg = String.format("""
                <svg width="%d" height="%d" xmlns="http://www.w3.org/2000/svg">
                %s
                </svg>
                """, getWidth(), getHeight(), createSvg(model));

        try {
            Files.writeString(Path.of("task3/svg", getFileName()), fullSvg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
