import fri.shapesge.FontStyle;
import fri.shapesge.Kruh;
import fri.shapesge.Manazer;
import fri.shapesge.Stvorec;
import fri.shapesge.Text;
import fri.shapesge.Trojuholnik;

public class Main {
    public static void main(String[] args) {
        Stvorec square = new Stvorec();
        square.zobraz();

        Kruh circle = new Kruh();
        circle.zobraz();

        Trojuholnik triangle = new Trojuholnik();
        triangle.zobraz();

        Text t = new Text("Búú zo\nSBGE");
        t.zmenFont("Serif", FontStyle.BOLD, 30);
        t.zmenFarbu("blue");
        t.zobraz();

        Manazer manager = new Manazer();
        manager.spravujObjekt(triangle);
        manager.spravujObjekt(new ManagedTest(circle));
    }

    @SuppressWarnings("unused")
    public static class ManagedTest {
        private final Kruh circle;

        public ManagedTest(Kruh circle) {
            this.circle = circle;
        }

        public void moveRight() {
            System.out.println("Move right");
        }

        public void vyberSuradnice(int x, int y) {
            System.out.format("Choose %d,%d%n", x, y);
        }

        public void mysPosunuta(int x, int y) {
            System.out.format("Mouse moved %d,%d%n", x, y);
        }

        public void tik() {
            System.out.println("Tick - tack");
            this.circle.posunVpravo();
        }
    }
}
