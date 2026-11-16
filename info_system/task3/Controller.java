package task3;

import java.util.Scanner;

public class Controller {
    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void startConsoleInput() {
        Scanner scanner = new Scanner(System.in);

        model.notifyObservers();
        while (true) {
            System.out.print("Какое значение изменить? (a, b, c) 'exit' для выхода\n:");
            String choice = scanner.next().trim().toLowerCase();

            if (choice.equals("exit")) {
                break;
            }

            if (choice.equals("a") || choice.equals("1")) {
                System.out.print("Введите число для a: ");
                int val = scanner.nextInt();
                model.setA(val);

            } else if (choice.equals("b") || choice.equals("2")) {
                System.out.print("Введите число для b: ");
                int val = scanner.nextInt();
                model.setB(val);

            } else if (choice.equals("c") || choice.equals("3")) {
                System.out.print("Введите число для c: ");
                int val = scanner.nextInt();
                model.setC(val);

            } else {
                System.out.println("Ошибка введите число буквы, или букву");
            }

            System.out.println();
        }

        scanner.close();
        System.out.println("Консольный ввод завершён.");
    }
}
