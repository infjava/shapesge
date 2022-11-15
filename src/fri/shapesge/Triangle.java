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

@SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    public Triangle() {
        this(50, 15);
    }

    @SuppressWarnings("unused")
    public Triangle(int x, int y) {
        this.height = 30;
        this.width = 40;
        this.xPosition = x;
        this.yPosition = y;
        this.color = Color.green;
        this.isVisible = false;

        this.drawable = new TriangleDrawable();
    }

    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    public void moveRight() {
        this.moveHorizontal(20);
    }

    /**
     * Move the triangle a few pixels to the left.
     */
    @SuppressWarnings("unused")
    public void moveLeft() {
        this.moveHorizontal(-20);
    }

    /**
     * Move the triangle a few pixels up.
     */
    @SuppressWarnings("unused")
    public void moveUp() {
        this.moveVertical(-20);
    }

    /**
     * Move the triangle a few pixels down.
     */
    @SuppressWarnings("unused")
    public void moveDown() {
        this.moveVertical(20);
    }

    /**
     * Move the triangle horizontally by 'distance' pixels.
     */
    @SuppressWarnings("unused")
    public void moveHorizontal(int distance) {
        this.xPosition += distance;
    }

    /**
     * Move the triangle vertically by 'distance' pixels.
     */
    @SuppressWarnings("unused")
    public void moveVertical(int distance) {
        this.yPosition += distance;
    }

    /**
     * Change the size to the new size (in pixels). Size must be greater or equal 0.
     */
    @SuppressWarnings("unused")
    public void changeSize(int newHeight, int newWidth) {
        this.height = newHeight;
        this.width = newWidth;
    }

    /**
     * Change the color.
     */
    @SuppressWarnings("unused")
    public void changeColor(String newColor) {
        this.color = Game.getGame().getParser().parseColor(newColor);
    }

    private class TriangleDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Triangle.this.isVisible) {
                return;
            }

            var xPoints = new int[]{Triangle.this.xPosition, Triangle.this.xPosition + (Triangle.this.width / 2), Triangle.this.xPosition - Triangle.this.width / 2};
            var yPoints = new int[]{Triangle.this.yPosition, Triangle.this.yPosition + Triangle.this.height, Triangle.this.yPosition + Triangle.this.height};
            var shape = new Polygon(xPoints, yPoints, 3);
            canvas.setColor(Triangle.this.color);
            canvas.fill(shape);
        }
    }
}
