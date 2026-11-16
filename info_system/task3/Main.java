package task3;

public class Main {
    public static void main(String[] args) {
        Model model = new Model(50, 30, 20);

        View1 v1 = new View1();
        View2 v2 = new View2();
        View3 v3 = new View3();

        model.registerObserver(new BorderDecorator(v1));
        model.registerObserver(new BorderDecorator(v2));
        model.registerObserver(new BorderDecorator(v3));

        Controller controller = new Controller(model);
        controller.startConsoleInput();
    }
}
