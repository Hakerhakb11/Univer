package task4;

import java.io.FileWriter;
import java.io.IOException;

public class SVGWriter {
    public static void save(String content, String filename, int width, int height) {
        String svg = String.format("""
                <svg xmlns='http://www.w3.org/2000/svg' width='%d' height='%d'>%s</svg>""", width, height, content);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(svg);
            System.out.println("\nSvg файл сохранен: " + filename + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла " + filename);
            e.printStackTrace();
        }
    }
}
