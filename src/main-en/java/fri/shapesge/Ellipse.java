package fri.shapesge;

import fri.shapesge.drawables.EllipticalDrawable;

import java.awt.Color;

/**
 * A ellipse that can be manipulated and that draws itself on a canvas.
 *
 * @author original: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

@SuppressWarnings("unused")
public class Ellipse {
    private final EllipticalDrawable drawable;

    /**
     * Create a new ellipse at default position with default color.
     */
    @SuppressWarnings("unused")
    public Ellipse() {
        this(20, 60);
    }

    @SuppressWarnings("unused")
    public Ellipse(int x, int y) {
        this.drawable = new EllipticalDrawable(x, y, 60, 30, Color.blue);
    }

    /**
     * Make this ellipse visible. If it was already visible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeVisible() {
        this.drawable.makeVisible();
    }

    /**
     * Make this ellipse invisible. If it was already invisible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeInvisible() {
        this.drawable.makeInvisible();
    }

    /**
     * Move the ellipse a few pixels to the right.
     */
    @SuppressWarnings("unused")
    public void moveRight() {
        this.drawable.moveBy(20, 0);
    }

    /**
     * Move the ellipse a few pixels to the left.
     */
    @SuppressWarnings("unused")
    public void moveLeft() {
        this.drawable.moveBy(-20, 0);
    }

    /**
     * Move the ellipse a few pixels up.
     */
    @SuppressWarnings("unused")
    public void moveUp() {
        this.drawable.moveBy(0, -20);
    }

    /**
     * Move the ellipse a few pixels down.
     */
    @SuppressWarnings("unused")
    public void moveDown() {
        this.drawable.moveBy(0, 20);
    }

    /**
     * Move the ellipse horizontally by 'distance' pixels.
     */
    @SuppressWarnings("unused")
    public void moveHorizontal(int distance) {
        this.drawable.moveBy(distance, 0);
    }

    /**
     * Move the ellipse vertically by 'distance' pixels.
     */
    @SuppressWarnings("unused")
    public void moveVertical(int distance) {
        this.drawable.moveBy(0, distance);
    }

    /**
     * Change the size to the new size (in pixels). Size must be greater or equal 0.
     */
    @SuppressWarnings("unused")
    public void changeSize(int newDiameterX, int newDiameterY) {
        this.drawable.changeSize(newDiameterX, newDiameterY);
    }

    /**
     * Change the color.
     */
    @SuppressWarnings("unused")
    public void changeColor(String newColor) {
        this.drawable.changeColor(newColor);
    }
}
