package fri.shapesge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Elipsa, s ktorou možno pohybovať a nakreslí sa na plátno.
 *
 * @author originál: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

@SuppressWarnings("unused")
public class Elipsa {
    private final EllipseDrawable drawable;
    private int diameterX;
    private int diameterY;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;

    /**
     * Vytvor novú elipsu preddefinovanej farby na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Elipsa() {
        this(20, 60);
    }

    @SuppressWarnings("unused")
    public Elipsa(int x, int y) {
        this.diameterX = 60;
        this.diameterY = 30;
        this.xPosition = x;
        this.yPosition = y;
        this.color = Color.blue;
        this.isVisible = false;

        this.drawable = new EllipseDrawable();
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
     * Zmeň veľkosti osí na hodnoty dané parametrami.
     * Veľkosť musí byť nezáporné celé číslo.
     */
    @SuppressWarnings("unused")
    public void zmenOsi(int osX, int osY) {
        this.diameterX = osX;
        this.diameterY = osY;
    }

    /**
     * Zmeň farbu na hodnotu danú parametrom.
     */
    @SuppressWarnings("unused")
    public void zmenFarbu(String newColor) {
        this.color = Game.getGame().getParser().parseColor(newColor);
    }

    private class EllipseDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Elipsa.this.isVisible) {
                return;
            }

            var shape = new Ellipse2D.Double(Elipsa.this.xPosition, Elipsa.this.yPosition, Elipsa.this.diameterX, Elipsa.this.diameterY);
            canvas.setColor(Elipsa.this.color);
            canvas.fill(shape);
        }
    }
}
