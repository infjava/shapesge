package fri.shapesge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * Rovnoramenný trojuholník, s ktorým možno pohybovať a nakreslí sa na plátno.
 *
 * @author originál: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

@SuppressWarnings("unused")
public class Trojuholnik {
    private final TriangleDrawable drawable;
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;

    /**
     * Vytvor nový rovnoramenný trojuholník preddefinovanej farby na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Trojuholnik() {
        this(50, 15);
    }

    @SuppressWarnings("unused")
    public Trojuholnik(int x, int y) {
        this.height = 30;
        this.width = 40;
        this.xPosition = x;
        this.yPosition = y;
        this.color = Color.green;
        this.isVisible = false;

        this.drawable = new TriangleDrawable();
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
        this.isVisible = true;
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
     * Zmeň rozmery výšky a základne na hodnoty dané parametrami.
     * Obe hodnoty musia byť nezáporné celé čísla.
     */
    @SuppressWarnings("unused")
    public void zmenRozmery(int vyska, int zakladna) {
        this.height = vyska;
        this.width = zakladna;
    }

    /**
     * Zmeň farbu na hodnotu danú parametrom.
     */
    @SuppressWarnings("unused")
    public void zmenFarbu(String farba) {
        this.color = Game.getGame().getParser().parseColor(farba);
    }

    private class TriangleDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Trojuholnik.this.isVisible) {
                return;
            }

            var xPoints = new int[]{Trojuholnik.this.xPosition, Trojuholnik.this.xPosition + (Trojuholnik.this.width / 2), Trojuholnik.this.xPosition - Trojuholnik.this.width / 2};
            var yPoints = new int[]{Trojuholnik.this.yPosition, Trojuholnik.this.yPosition + Trojuholnik.this.height, Trojuholnik.this.yPosition + Trojuholnik.this.height};
            var shape = new Polygon(xPoints, yPoints, 3);
            canvas.setColor(Trojuholnik.this.color);
            canvas.fill(shape);
        }
    }
}
