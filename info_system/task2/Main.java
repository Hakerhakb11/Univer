package task2;

public class Main {
    public static void main(String[] args) {
        Model model = new Model(50, 30, 20);

        View1 v1 = new View1();
        View2 v2 = new View2();
        View3 v3 = new View3();

        model.registerObserver(v1);
        model.registerObserver(v2);
        model.registerObserver(v3);

        Controller controller = new Controller(model);
        controller.startConsoleInput();
    }
}
