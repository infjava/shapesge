package fri.shapesge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Štvorec, s ktorým možno pohybovať a nakreslí sa na plátno.
 *
 * @author originál: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */
@SuppressWarnings("unused")
public class Stvorec {
    private final SquareDrawable drawable;
    private int size;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;

    /**
     * Vytvor nový štvorec preddefinovanej farby na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Stvorec() {
        this(60, 50);
    }

    @SuppressWarnings("unused")
    public Stvorec(int x, int y) {
        this.size = 30;
        this.xPosition = x;
        this.yPosition = y;
        this.color = Color.red;
        this.isVisible = false;

        this.drawable = new SquareDrawable();
    }

    /**
     * Zobraz sa.
     */
    @SuppressWarnings("unused")
    public void zobraz() {
        if (this.isVisible) {
            return;
        }

        Game.getGame().registerDrawable(this.drawable);
        this.isVisible = true;
    }

    /**
     * Skry sa.
     */
    @SuppressWarnings("unused")
    public void skry() {
        if (!this.isVisible) {
            return;
        }

        Game.getGame().unregisterDrawable(this.drawable);
        this.isVisible = false;
    }

    /**
     * Posuň sa vpravo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVpravo() {
        this.posunVodorovne(20);
    }

    /**
     * Posuň sa vľavo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVlavo() {
        this.posunVodorovne(-20);
    }

    /**
     * Posuň sa hore o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunHore() {
        this.posunZvisle(-20);
    }

    /**
     * Posuň sa dole o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunDole() {
        this.posunZvisle(20);
    }

    /**
     * Posuň sa vodorovne o dĺžku danú parametrom.
     */
    @SuppressWarnings("unused")
    public void posunVodorovne(int vzdialenost) {
        this.xPosition += vzdialenost;
    }

    /**
     * Posuň sa zvisle o dĺžku danú parametrom.
     */
    @SuppressWarnings("unused")
    public void posunZvisle(int vzdialenost) {
        this.yPosition += vzdialenost;
    }

    /**
     * Zmeň dĺžku strany na hodnotu danú parametrom.
     * Dĺžka strany musí byť nezáporné celé číslo.
     */
    @SuppressWarnings("unused")
    public void zmenStranu(int dlzka) {
        this.size = dlzka;
    }

    /**
     * Zmeň farbu na hodnotu danú parametrom.
     */
    @SuppressWarnings("unused")
    public void zmenFarbu(String farba) {
        this.color = Game.getGame().getParser().parseColor(farba);
    }

    private class SquareDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Stvorec.this.isVisible) {
                return;
            }

            var shape = new Rectangle2D.Double(Stvorec.this.xPosition, Stvorec.this.yPosition, Stvorec.this.size, Stvorec.this.size);
            canvas.setColor(Stvorec.this.color);
            canvas.fill(shape);
        }
    }
}
