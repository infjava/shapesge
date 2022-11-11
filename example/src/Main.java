import fri.shapesge.Circle;
import fri.shapesge.Manager;
import fri.shapesge.Square;
import fri.shapesge.Triangle;

public class Main {
    public static void main(String[] args) {
        Square square = new Square();
        square.makeVisible();

        Circle circle = new Circle();
        circle.makeVisible();

        Triangle triangle = new Triangle();
        triangle.makeVisible();

        Manager manager = new Manager();
        manager.manageObject(triangle);
    }
}