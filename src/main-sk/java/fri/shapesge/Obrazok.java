package fri.shapesge;

import fri.shapesge.drawables.ImageDrawable;
import fri.shapesge.engine.Game;

/**
 * Trieda Obrazok, reprezentuje bitmapový obrázok, ktorý môže byť vykreslený na plátno.
 *
 * @author originál: Miroslav Kvaššay and Michal Varga
 * @author engine: Ján Janech
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Obrazok {
    private final ImageDrawable drawable;

    /**
     * Vytvor nový obrázok s danou cestou na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Obrazok(String suborSObrazkom) {
        this(suborSObrazkom, 100, 100);
    }

    @SuppressWarnings("unused")
    public Obrazok(String suborSObrazkom, int x, int y) {
        var image = Game.getGame().getParser().parseImage(suborSObrazkom);
        this.drawable = new ImageDrawable(x, y, 0, image);
    }

    /**
     * Zobraz sa.
     */
    @SuppressWarnings("unused")
    public void zobraz() {
        this.drawable.makeVisible();
    }

    /**
     * Skry sa.
     */
    @SuppressWarnings("unused")
    public void skry() {
        this.drawable.makeInvisible();
    }

    /**
     * Posuň sa vpravo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVpravo() {
        this.drawable.moveBy(20, 0);
    }

    /**
     * Posuň sa vľavo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVlavo() {
        this.drawable.moveBy(-20, 0);
    }

    /**
     * Posuň sa hore o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunHore() {
        this.drawable.moveBy(0, -20);
    }

    /**
     * Posuň sa dole o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunDole() {
        this.drawable.moveBy(0, 20);
    }

    /**
     * Posuň sa zvisle o dĺžku danú parametrom.
     */
    @SuppressWarnings("unused")
    public void posunVodorovne(int vzdialenost) {
        this.drawable.moveBy(vzdialenost, 0);
    }

    /**
     * Posuň sa pomaly vodorovne o dĺžku danú parametrom.
     */
    @SuppressWarnings("unused")
    public void posunZvisle(int vzdialenost) {
        this.drawable.moveBy(0, vzdialenost);
    }

    /**
     * Zmeň obrázok.
     * Súbor s obrázkom musí existovať.
     */
    @SuppressWarnings("unused")
    public void zmenObrazok(String suborSObrazkom) {
        this.drawable.changeImage(suborSObrazkom);
    }

    /**
     * Zmeň uhol natočenia obrázku podľa parametra. Sever = 0.
     */
    @SuppressWarnings("unused")
    public void zmenUhol(int uhol) {
        this.drawable.changeAngle(uhol);
    }
}
