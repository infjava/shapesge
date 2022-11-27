import fri.shapesge.*;

public class Main {
    private static final Kruh circle = new Kruh();

    public static void main(String[] args) {
        Stvorec square = new Stvorec();
        square.zobraz();

        circle.zobraz();

        Trojuholnik triangle = new Trojuholnik();
        triangle.zobraz();

        Text t = new Text("Búú zo SBGE");
        t.zmenFont("Serif", FontStyle.BOLD, 30);
        t.zmenFarbu("blue");
        t.zobraz();

        Manazer manager = new Manazer();
        manager.spravujObjekt(triangle);
        manager.spravujObjekt(new ManagedTest());
    }

    @SuppressWarnings("unused")
    public static class ManagedTest {
        public void posunVpravo() {
            System.out.println("Move right");
        }

        public void vyberSuradnice(int x, int y) {
            System.out.format("Choose %d,%d%n", x, y);
        }

        public void tik() {
            System.out.println("Tick - tack");
            circle.posunVpravo();
        }
    }
}
