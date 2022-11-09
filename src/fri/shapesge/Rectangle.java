package fri.shapesge;

/**
 * A square that can be manipulated and that draws itself on a canvas.
 *
 * @author original: Michael Kölling and David J. Barnes
 * @author engine: Ján Janech
 * @version 1.0  (9.11.2022)
 */
public class Rectangle {
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    /**
     * Create a new square at default position with default color.
     */
    public Rectangle() {
        this.width = 30;
        this.height = 60;
        this.xPosition = 60;
        this.yPosition = 50;
        this.color = "red";
        this.isVisible = false;
    }

    /**
     * Make this square visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        this.isVisible = true;
    }

    /**
     * Make this square invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        this.isVisible = false;
    }

    /**
     * Move the square a few pixels to the right.
     */
    public void moveRight() {
        this.moveHorizontal(20);
    }

    /**
     * Move the square a few pixels to the left.
     */
    public void moveLeft() {
        this.moveHorizontal(-20);
    }

    /**
     * Move the square a few pixels up.
     */
    public void moveUp() {
        this.moveVertical(-20);
    }

    /**
     * Move the square a few pixels down.
     */
    public void moveDown() {
        this.moveVertical(20);
    }

    /**
     * Move the square horizontally by 'distance' pixels.
     */
    public void moveHorizontal(int distance) {
        this.xPosition += distance;
    }

    /**
     * Move the square vertically by 'distance' pixels.
     */
    public void moveVertical(int distance) {
        this.yPosition += distance;
    }

    /**
     * Change the size to the new size (in pixels). Size must be >= 0.
     */
    public void changeSize(int newWidth, int newHeight) {
        this.width = newWidth;
        this.height = newHeight;
    }

    /**
     * Change the color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor) {
        this.color = newColor;
    }
}
