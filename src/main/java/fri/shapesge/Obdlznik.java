package fri.shapesge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Obdĺžnik, s ktorým možno pohybovať a nakreslí sa na plátno.
 *
 * @author originál: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */
@SuppressWarnings("unused")
public class Obdlznik {
    private final RectangleDrawable drawable;
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;

    /**
     * Vytvor nový obdĺžnik preddefinovanej farby na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Obdlznik() {
        this(60, 50);
    }

    @SuppressWarnings("unused")
    public Obdlznik(int x, int y) {
        this.width = 30;
        this.height = 60;
        this.xPosition = x;
        this.yPosition = y;
        this.color = Color.red;
        this.isVisible = false;

        this.drawable = new RectangleDrawable();
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
     * Posuň sa hore o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVlavo() {
        this.posunVodorovne(-20);
    }

    /**
     * Posuň sa dole o pevnú dĺžku.
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
     * Zmeň dĺžky strán na hodnoty dané parametrami.
     * Dĺžka strany musí byť nezáporné celé číslo.
     */
    @SuppressWarnings("unused")
    public void zmenStrany(int stranaA, int stranaB) {
        this.width = stranaA;
        this.height = stranaB;
    }

    /**
     * Zmeň farbu na hodnotu danú parametrom.
     */
    @SuppressWarnings("unused")
    public void zmenFarbu(String farba) {
        this.color = Game.getGame().getParser().parseColor(farba);
    }

    private class RectangleDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Obdlznik.this.isVisible) {
                return;
            }

            var shape = new Rectangle2D.Double(Obdlznik.this.xPosition, Obdlznik.this.yPosition, Obdlznik.this.width, Obdlznik.this.height);
            canvas.setColor(Obdlznik.this.color);
            canvas.fill(shape);
        }
    }
}
