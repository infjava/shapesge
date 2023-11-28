package fri.shapesge;

import fri.shapesge.drawables.TriangularDrawable;

import java.awt.Color;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 *
 * @author original: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

@SuppressWarnings("unused")
public class Triangle {
    private final TriangularDrawable drawable;

    /**
     * Create a new triangle at default position with default color.
     */
    @SuppressWarnings("unused")
    public Triangle() {
        this(50, 15);
    }

    /**
     * Create a new triangle at a given position with default color.
     * @param x x-coordinate of the triangle
     *          (distance from left border of the canvas)
     * @param y y-coordinate of the triangle
     *          (distance from top border of the canvas)
     */
    @SuppressWarnings("unused")
    public Triangle(int x, int y) {
        this.drawable = new TriangularDrawable(x, y, 40, 30, Color.green);
    }

    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeVisible() {
        this.drawable.makeVisible();
    }

    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeInvisible() {
        this.drawable.makeInvisible();
    }

    /**
     * Move the triangle a few pixels to the right.
     */
    @SuppressWarnings("unused")
    public void moveRight() {
        this.drawable.moveBy(20, 0);
    }

    /**
     * Move the triangle a few pixels to the left.
     */
    @SuppressWarnings("unused")
    public void moveLeft() {
        this.drawable.moveBy(-20, 0);
    }

    /**
     * Move the triangle a few pixels up.
     */
    @SuppressWarnings("unused")
    public void moveUp() {
        this.drawable.moveBy(0, -20);
    }

    /**
     * Move the triangle a few pixels down.
     */
    @SuppressWarnings("unused")
    public void moveDown() {
        this.drawable.moveBy(0, 20);
    }

    /**
     * Move the triangle horizontally by 'distance' pixels.
     * @param distance distance in pixels
     */
    @SuppressWarnings("unused")
    public void moveHorizontal(int distance) {
        this.drawable.moveBy(distance, 0);
    }

    /**
     * Move the triangle vertically by 'distance' pixels.
     * @param distance distance in pixels
     */
    @SuppressWarnings("unused")
    public void moveVertical(int distance) {
        this.drawable.moveBy(0, distance);
    }

    /**
     * Change the size to the new size (in pixels). Size must be greater or equal 0.
     * @param newHeight new height in pixels
     * @param newWidth new width in pixels
     */
    @SuppressWarnings("unused")
    public void changeSize(int newHeight, int newWidth) {
        this.drawable.changeSize(newWidth, newHeight);
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
     * @param x x-coordinate of the triangle
     *          (distance from left border of the canvas)
     * @param y y-coordinate of the triangle
     *          (distance from top border of the canvas)
     */
    @SuppressWarnings("unused")
    public void changePosition(int x, int y) {
        this.drawable.moveTo(x, y);
    }
}
