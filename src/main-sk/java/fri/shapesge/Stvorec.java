package fri.shapesge;

import fri.shapesge.drawables.RectangularDrawable;

import java.awt.Color;

/**
 * Štvorec, s ktorým možno pohybovať a nakreslí sa na plátno.
 *
 * @author originál: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */
@SuppressWarnings("unused")
public class Stvorec {
    private final RectangularDrawable drawable;

    /**
     * Vytvor nový štvorec preddefinovanej farby na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Stvorec() {
        this(60, 50);
    }

    /**
     * Vytvor nový štvorec preddefinovanej farby na danej pozícii.
     * @param x x-ová súradnica štvorca
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica štvorca
     *          (vzdialenosť od horného okraja plátna)
     */
    @SuppressWarnings("unused")
    public Stvorec(int x, int y) {
        this.drawable = new RectangularDrawable(x, y, 30, 30, Color.red);
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
     * Zmeň dĺžku strany na hodnotu danú parametrom.
     * Dĺžka strany musí byť nezáporné celé číslo.
     * @param dlzka nová dĺžka v pixeloch
     */
    @SuppressWarnings("unused")
    public void zmenStranu(int dlzka) {
        this.drawable.changeSize(dlzka, dlzka);
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
     * Zmeň polohu štvorca na hodnoty dané parametrami.
     * @param x x-ová súradnica štvorca
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica štvorca
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
     * @return šírka/výška tvaru
     */
    public int getVelkost() {
        return this.drawable.getWidth();
    }
}
