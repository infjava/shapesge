package fri.shapesge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 *
 * @author original: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

public class Ellipse {
    private final EllipseDrawable drawable;
    private int diameterX;
    private int diameterY;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;

    /**
     * Create a new circle at default position with default color.
     */
    public Ellipse() {
        this.diameterX = 60;
        this.diameterY = 30;
        this.xPosition = 20;
        this.yPosition = 60;
        this.color = Color.blue;
        this.isVisible = false;

        this.drawable = new EllipseDrawable();
    }

    /**
     * Make this circle visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        if (this.isVisible) {
            return;
        }

        Game.getGame().registerDrawable(this.drawable);
        this.isVisible = true;
    }

    /**
     * Make this circle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        if (!this.isVisible) {
            return;
        }

        Game.getGame().unregisterDrawable(this.drawable);
        this.isVisible = true;
    }

    /**
     * Move the circle a few pixels to the right.
     */
    public void moveRight() {
        this.moveHorizontal(20);
    }

    /**
     * Move the circle a few pixels to the left.
     */
    public void moveLeft() {
        this.moveHorizontal(-20);
    }

    /**
     * Move the circle a few pixels up.
     */
    public void moveUp() {
        this.moveVertical(-20);
    }

    /**
     * Move the circle a few pixels down.
     */
    public void moveDown() {
        this.moveVertical(20);
    }

    /**
     * Move the circle horizontally by 'distance' pixels.
     */
    public void moveHorizontal(int distance) {
        this.xPosition += distance;
    }

    /**
     * Move the circle vertically by 'distance' pixels.
     */
    public void moveVertical(int distance) {
        this.yPosition += distance;
    }

    /**
     * Change the size to the new size (in pixels). Size must be greater or equal 0.
     */
    public void changeSize(int newDiameterX, int newDiameterY) {
        this.diameterX = newDiameterX;
        this.diameterY = newDiameterY;
    }

    /**
     * Change the color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor) {
        this.color = Parser.parseColor(newColor);
    }

    private class EllipseDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Ellipse.this.isVisible) {
                return;
            }

            var shape = new Ellipse2D.Double(Ellipse.this.xPosition, Ellipse.this.yPosition, Ellipse.this.diameterX, Ellipse.this.diameterY);
            canvas.setColor(Ellipse.this.color);
            canvas.fill(shape);
        }
    }
}
