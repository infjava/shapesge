package fri.shapesge;

import fri.shapesge.drawables.EllipticalDrawable;

import java.awt.Color;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 *
 * @author original: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

@SuppressWarnings("unused")
public class Circle {
    private final EllipticalDrawable drawable;

    /**
     * Create a new circle at default position with default color.
     */
    @SuppressWarnings("unused")
    public Circle() {
        this(20, 60);
    }

    /**
     * Create a new circle at a given position with default color.
     * @param x x-coordinate of the circle
     *          (distance from left border of the canvas)
     * @param y y-coordinate of the circle
     *          (distance from top border of the canvas)
     */
    @SuppressWarnings("unused")
    public Circle(int x, int y) {
        this.drawable = new EllipticalDrawable(x, y, 30, 30, Color.blue);
    }

    /**
     * Make this circle visible. If it was already visible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeVisible() {
        this.drawable.makeVisible();
    }

    /**
     * Make this circle invisible. If it was already invisible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeInvisible() {
        this.drawable.makeInvisible();
    }

    /**
     * Move the circle a few pixels to the right.
     */
    @SuppressWarnings("unused")
    public void moveRight() {
        this.drawable.moveBy(20, 0);
    }

    /**
     * Move the circle a few pixels to the left.
     */
    @SuppressWarnings("unused")
    public void moveLeft() {
        this.drawable.moveBy(-20, 0);
    }

    /**
     * Move the circle a few pixels up.
     */
    @SuppressWarnings("unused")
    public void moveUp() {
        this.drawable.moveBy(0, -20);
    }

    /**
     * Move the circle a few pixels down.
     */
    @SuppressWarnings("unused")
    public void moveDown() {
        this.drawable.moveBy(0, 20);
    }

    /**
     * Move the circle horizontally by 'distance' pixels.
     * @param distance distance in pixels
     */
    @SuppressWarnings("unused")
    public void moveHorizontal(int distance) {
        this.drawable.moveBy(distance, 0);
    }

    /**
     * Move the circle vertically by 'distance' pixels.
     * @param distance distance in pixels
     */
    @SuppressWarnings("unused")
    public void moveVertical(int distance) {
        this.drawable.moveBy(0, distance);
    }

    /**
     * Change the size to the new size (in pixels). Size must be greater or equal 0.
     * @param newDiameter new diameter in pixels
     */
    @SuppressWarnings("unused")
    public void changeSize(int newDiameter) {
        this.drawable.changeSize(newDiameter, newDiameter);
    }

    /**
     * Change the color.
     * @param newColor new color from palette or in #rrggbb format
     */
    @SuppressWarnings("unused")
    public void changeColor(String newColor) {
        this.drawable.changeColor(newColor);
    }

    /**
     * Change the position according to the parameters.
     * @param x x-coordinate of the circle
     *          (distance from left border of the canvas)
     * @param y y-coordinate of the circle
     *          (distance from top border of the canvas)
     */
    @SuppressWarnings("unused")
    public void changePosition(int x, int y) {
        this.drawable.moveTo(x, y);
    }
}
