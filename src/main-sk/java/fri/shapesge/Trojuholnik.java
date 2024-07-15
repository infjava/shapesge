package fri.shapesge;

import fri.shapesge.drawables.TriangularDrawable;

import java.awt.Color;

/**
 * Rovnoramenný trojuholník, s ktorým možno pohybovať a nakreslí sa na plátno.
 *
 * @author originál: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

@SuppressWarnings("unused")
public class Trojuholnik {
    private final TriangularDrawable drawable;

    /**
     * Vytvor nový rovnoramenný trojuholník preddefinovanej farby na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Trojuholnik() {
        this(50, 15);
    }

    /**
     * Vytvor nový rovnoramenný trojuholník preddefinovanej farby na danej pozícii.
     * @param x x-ová súradnica trojuholníka
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica trojuholníka
     *          (vzdialenosť od horného okraja plátna)
     */
    @SuppressWarnings("unused")
    public Trojuholnik(int x, int y) {
        this.drawable = new TriangularDrawable(x, y, 40, 30, Color.green);
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
     * Posuň sa vodorovne o dĺžku danú parametrom.
     * @param vzdialenost vzdialenosť v pixeloch
     */
    @SuppressWarnings("unused")
    public void posunVodorovne(int vzdialenost) {
        this.drawable.moveBy(vzdialenost, 0);
    }

    /**
     * Posuň sa zvisle o dĺžku danú parametrom.
     * @param vzdialenost vzdialenosť v pixeloch
     */
    @SuppressWarnings("unused")
    public void posunZvisle(int vzdialenost) {
        this.drawable.moveBy(0, vzdialenost);
    }

    /**
     * Zmeň rozmery výšky a základne na hodnoty dané parametrami.
     * Obe hodnoty musia byť nezáporné celé čísla.
     * @param vyska nová výška v pixeloch
     * @param zakladna nová základňa trojuholníka v pixeloch
     */
    @SuppressWarnings("unused")
    public void zmenRozmery(int vyska, int zakladna) {
        this.drawable.changeSize(zakladna, vyska);
    }

    /**
     * Zmeň farbu na hodnotu danú parametrom.
     * @param farba nová farba z palety alebo v tvare #rrggbb
     */
    @SuppressWarnings("unused")
    public void zmenFarbu(String farba) {
        this.drawable.changeColor(farba);
    }

    /**
     * Zmeň polohu trojuholníka na hodnoty dané parametrami.
     * @param x x-ová súradnica trojuholníka
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica trojuholníka
     *          (vzdialenosť od horného okraja plátna)
     */
    @SuppressWarnings("unused")
    public void zmenPolohu(int x, int y) {
        this.drawable.moveTo(x, y);
    }

    /**
     * @return x-ová súradnica (vzdialenosť od ľavého okraja) tvaru
     */
    public int getPoziciaX() {
        return this.drawable.getXPosition();
    }

    /**
     * @return y-ová súradnica (vzdialenosť od horného okraja) tvaru
     */
    public int getPoziciaY() {
        return this.drawable.getYPosition();
    }

    /**
     * @return šírka tvaru
     */
    public int getSirka() {
        return this.drawable.getWidth();
    }

    /**
     * @return výška tvaru
     */
    public int getVyska() {
        return this.drawable.getHeight();
    }
}
