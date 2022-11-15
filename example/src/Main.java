import fri.shapesge.Kruh;
import fri.shapesge.Manazer;
import fri.shapesge.Stvorec;
import fri.shapesge.Trojuholnik;

public class Main {
    private static final Kruh circle = new Kruh();

    public static void main(String[] args) {
        Stvorec square = new Stvorec();
        square.makeVisible();

        circle.makeVisible();

        Trojuholnik triangle = new Trojuholnik();
        triangle.makeVisible();

        Manazer manager = new Manazer();
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

        public void tick() {
            System.out.println("Tick - tack");
            circle.moveRight();
        }
    }
}
