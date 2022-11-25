package fri.shapesge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Kruh, s ktorým možno pohybovať a nakreslí sa na plátno.
 *
 * @author originál: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

@SuppressWarnings("unused")
public class Kruh {
    private final CircleDrawable drawable;
    private int diameter;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;

    /**
     * Vytvor nový kruh preddefinovanej farby na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Kruh() {
        this(20, 60);
    }

    @SuppressWarnings("unused")
    public Kruh(int x, int y) {
        this.diameter = 30;
        this.xPosition = x;
        this.yPosition = y;
        this.color = Color.blue;
        this.isVisible = false;

        this.drawable = new CircleDrawable();
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
     * Zmeň priemer na hodnotu danú parametrom.
     * Priemer musí byť nezáporné celé číslo.
     */
    @SuppressWarnings("unused")
    public void zmenPriemer(int priemer) {
        this.diameter = priemer;
    }

    /**
     * Zmeň farbu na hodnotu danú parametrom.
     */
    @SuppressWarnings("unused")
    public void zmenFarbu(String farba) {
        this.color = Game.getGame().getParser().parseColor(farba);
    }

    private class CircleDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Kruh.this.isVisible) {
                return;
            }

            var shape = new Ellipse2D.Double(Kruh.this.xPosition, Kruh.this.yPosition, Kruh.this.diameter, Kruh.this.diameter);
            canvas.setColor(Kruh.this.color);
            canvas.fill(shape);
        }
    }
}