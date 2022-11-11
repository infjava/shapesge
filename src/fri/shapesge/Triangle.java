package fri.shapesge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 *
 * @author original: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

public class Triangle {
    private final TriangleDrawable drawable;
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;

    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle() {
        this.height = 30;
        this.width = 40;
        this.xPosition = 50;
        this.yPosition = 15;
        this.color = Color.green;
        this.isVisible = false;

        this.drawable = new TriangleDrawable();
    }

    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        if (this.isVisible) {
            return;
        }

        Game.getGame().registerDrawable(this.drawable);
        this.isVisible = true;
    }

    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        if (!this.isVisible) {
            return;
        }

        Game.getGame().unregisterDrawable(this.drawable);
        this.isVisible = true;
    }

    /**
     * Move the triangle a few pixels to the right.
     */
    public void moveRight() {
        this.moveHorizontal(20);
    }

    /**
     * Move the triangle a few pixels to the left.
     */
    public void moveLeft() {
        this.moveHorizontal(-20);
    }

    /**
     * Move the triangle a few pixels up.
     */
    public void moveUp() {
        this.moveVertical(-20);
    }

    /**
     * Move the triangle a few pixels down.
     */
    public void moveDown() {
        this.moveVertical(20);
    }

    /**
     * Move the triangle horizontally by 'distance' pixels.
     */
    public void moveHorizontal(int distance) {
        this.xPosition += distance;
    }

    /**
     * Move the triangle vertically by 'distance' pixels.
     */
    public void moveVertical(int distance) {
        this.yPosition += distance;
    }

    /**
     * Change the size to the new size (in pixels). Size must be >= 0.
     */
    public void changeSize(int newHeight, int newWidth) {
        this.height = newHeight;
        this.width = newWidth;
    }

    /**
     * Change the color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor) {
        this.color = Parser.parseColor(newColor);
    }

    private class TriangleDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Triangle.this.isVisible) {
                return;
            }

            var xpoints = new int[]{Triangle.this.xPosition, Triangle.this.xPosition + (Triangle.this.width / 2), Triangle.this.xPosition - Triangle.this.width / 2};
            var ypoints = new int[]{Triangle.this.yPosition, Triangle.this.yPosition + Triangle.this.height, Triangle.this.yPosition + Triangle.this.height};
            var shape = new Polygon(xpoints, ypoints, 3);
            canvas.setColor(Triangle.this.color);
            canvas.fill(shape);
        }
    }
}
