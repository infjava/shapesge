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
        manager.manageObject(new ManagedTest());
    }

    @SuppressWarnings("unused")
    public static class ManagedTest {
        public void moveRight() {
            System.out.println("Move right");
        }

        public void chooseCoordinates(int x, int y) {
            System.out.format("Choose %d,%d%n", x, y);
        }
    }
}
