package fri.shapesge;

import fri.shapesge.drawables.EllipticalDrawable;

import java.awt.Color;

/**
 * Elipsa, s ktorou možno pohybovať a nakreslí sa na plátno.
 *
 * @author originál: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

@SuppressWarnings("unused")
public class Elipsa {
    private final EllipticalDrawable drawable;

    /**
     * Vytvor novú elipsu preddefinovanej farby na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Elipsa() {
        this(20, 60);
    }

/**
     * Vytvor novú elipsu preddefinovanej farby na danej pozícii.
     * @param x x-ová súradnica elipsy
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica elipsy
     *          (vzdialenosť od horného okraja plátna)
     */
    @SuppressWarnings("unused")
    public Elipsa(int x, int y) {
        this.drawable = new EllipticalDrawable(x, y, 60, 30, Color.blue);
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
     * Zmeň veľkosti osí na hodnoty dané parametrami.
     * Veľkosť musí byť nezáporné celé číslo.
     * @param osX veľkosť elipsy podľa osi X
     * @param osY veľkosť elipsy podľa osi Y
     */
    @SuppressWarnings("unused")
    public void zmenOsi(int osX, int osY) {
        this.drawable.changeSize(osX, osY);
    }

    /**
     * Zmeň farbu na hodnotu danú parametrom.
     * @param farba nová farba z palety alebo v tvare #rrggbb
     */
    @SuppressWarnings("unused")
    public void zmenFarbu(String farba) {
        this.drawable.changeColor(farba);
    }
}
