import fri.shapesge.Circle;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.Manager;
import fri.shapesge.Square;
import fri.shapesge.TextBlock;
import fri.shapesge.Triangle;

public class Main {
    public static void main(String[] args) {
        Square square = new Square();
        square.makeVisible();

        Circle circle = new Circle();
        circle.makeVisible();

        Triangle triangle = new Triangle();
        triangle.makeVisible();

        TextBlock t = new TextBlock("Boo from\nSBGE");
        t.changeFont("Serif", FontStyle.BOLD, 30);
        t.changeColor("blue");
        t.makeVisible();

        ImageData id = new ImageData("test.jpg");
        System.out.format("%d x %d%n", id.getWidth(), id.getHeight());
        Image i = new Image(id);
        i.makeVisible();

        Manager manager = new Manager();
        manager.manageObject(triangle);
        manager.manageObject(new ManagedTest(circle));
    }

    @SuppressWarnings("unused")
    public static class ManagedTest {
        private final Circle circle;

        public ManagedTest(Circle circle) {
            this.circle = circle;
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
            System.out.println("Tick - tack");
            this.circle.moveRight();
        }
    }
}
