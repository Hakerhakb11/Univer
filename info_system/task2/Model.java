package task2;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private int a, b, c;
    private List<Observer> observers = new ArrayList<>();

    public Model(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public void setA(int a) {
        this.a = a;
        normalize();
        notifyObservers();
    }

    public void setB(int b) {
        this.b = b;
        normalize();
        notifyObservers();
    }

    public void setC(int c) {
        this.c = c;
        normalize();
        notifyObservers();
    }

    private void normalize() {
        int sum = a + b + c;
        if (sum == 0) {
            a = 34;
            b = 33;
            c = 33;
            return;
        }
        if (sum != 100) {
            double factor = 100.0 / sum;
            a = (int) (a * factor);
            b = (int) (b * factor);
            c = 100 - a - b;
        }
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
