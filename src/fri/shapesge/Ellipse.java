package fri.shapesge;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 *
 * @author original: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */

public class Ellipse {
    private int diameterX;
    private int diameterY;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    /**
     * Create a new circle at default position with default color.
     */
    public Ellipse() {
        this.diameterX = 60;
        this.diameterY = 30;
        this.xPosition = 20;
        this.yPosition = 60;
        this.color = "blue";
        this.isVisible = false;
    }

    /**
     * Make this circle visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        this.isVisible = true;
    }

    /**
     * Make this circle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        this.isVisible = false;
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
     * Change the size to the new size (in pixels). Size must be >= 0.
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
        this.color = newColor;
    }
}
