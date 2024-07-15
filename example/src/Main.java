import fri.shapesge.Circle;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.Manager;
import fri.shapesge.Square;
import fri.shapesge.TextBlock;
import fri.shapesge.Triangle;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Square square = new Square();
        square.makeVisible();

        Circle circle = new Circle();
        circle.makeVisible();

        Triangle triangle = new Triangle();
        triangle.makeVisible();

        TextBlock t = new TextBlock("Boo from\nSBGE", 0, 0);
        t.changeFont("Serif", FontStyle.BOLD, 30);
        t.changeColor("blue");
        t.makeVisible();

        TextBlock t2 = new TextBlock("Foo Bar!!!", 60, 60);
        t2.changeFont("Sans", FontStyle.BOLD, 30);
        t2.changeColor("red");
        t2.makeVisible();

        ImageData id = new ImageData("test.jpg");
        System.out.format("%d x %d%n", id.getWidth(), id.getHeight());
        Image i = new Image(id);
        i.makeVisible();

        Circle randomCircle = new Circle(100, 100);
        randomCircle.changeColor("red");
        randomCircle.makeVisible();

        Manager manager = new Manager();
        manager.manageObject(triangle);
        manager.manageObject(new ManagedTest(circle, randomCircle));
    }

    @SuppressWarnings("unused")
    public static class ManagedTest {
        private final Circle circle;
        private final Circle randomCircle;

        public ManagedTest(Circle circle, Circle randomCircle) {
            this.circle = circle;
            this.randomCircle = randomCircle;
        }

        public void moveRight() {
            System.out.println("Move right");
        }

        public void chooseCoordinates(int x, int y) {
            System.out.format("Choose %d,%d%n", x, y);
        }

        public void mouseMove(int x, int y) {
            System.out.format("Mouse moved %d,%d%n", x, y);
        }

        public void boo() {
            System.out.println("You really want to quit? Why would you do that?");
        }

        public void tick() {
            Random r = new Random();
            System.out.println("Tick - tack");
            this.circle.moveRight();
            this.randomCircle.changePosition(r.nextInt(1000), r.nextInt(1000));
        }
    }
}
