package fri.shapesge;

import fri.shapesge.drawables.ImageDrawable;
import fri.shapesge.engine.Game;

/**
 * The Image class represents a bitmap image that can be drawn on the canvas.
 *
 * @author original: Miroslav Kvaššay and Michal Varga
 * @author engine: Ján Janech
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Image {
    private final ImageDrawable drawable;

    /**
     * Create a new image at default position.
     * @param imagePath path to the image
     */
    @SuppressWarnings("unused")
    public Image(String imagePath) {
        this(imagePath, 100, 100);
    }

    /**
     * Create a new image at a given position.
     *
     * @param imagePath path to the image
     * @param x x-coordinate of the image
     *          (distance from left border of the canvas)
     * @param y y-coordinate of the image
     *          (distance from top border of the canvas)
     */
    @SuppressWarnings("unused")
    public Image(String imagePath, int x, int y) {
        var image = Game.getGame().getParser().parseImage(imagePath);
        this.drawable = new ImageDrawable(x, y, 0, image);
    }

    /**
     * Make this image visible. If it was already visible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeVisible() {
        this.drawable.makeVisible();
    }

    /**
     * Make this image invisible. If it was already invisible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeInvisible() {
        this.drawable.makeInvisible();
    }

    /**
     * Move the image a few pixels to the right.
     */
    @SuppressWarnings("unused")
    public void moveRight() {
        this.drawable.moveBy(20, 0);
    }

    /**
     * Move the image a few pixels to the left.
     */
    @SuppressWarnings("unused")
    public void moveLeft() {
        this.drawable.moveBy(-20, 0);
    }

    /**
     * Move the image a few pixels up.
     */
    @SuppressWarnings("unused")
    public void moveUp() {
        this.drawable.moveBy(0, -20);
    }

    /**
     * Move the image a few pixels down.
     */
    @SuppressWarnings("unused")
    public void moveDown() {
        this.drawable.moveBy(0, 20);
    }

    /**
     * Move the image horizontally by 'distance' pixels.
     * @param distance distance in pixels
     */
    @SuppressWarnings("unused")
    public void moveHorizontal(int distance) {
        this.drawable.moveBy(distance, 0);
    }

    /**
     * Move the image vertically by 'distance' pixels.
     * @param distance distance in pixels
     */
    @SuppressWarnings("unused")
    public void moveVertical(int distance) {
        this.drawable.moveBy(0, distance);
    }

    /**
     * Change the drawn image. Image must exist.
     * @param imagePath path to the image
     */
    @SuppressWarnings("unused")
    public void changeImage(String imagePath) {
        this.drawable.changeImage(imagePath);
    }

    /**
     * Change the image rotation angle according to the parameter. North = 0.
     * @param angle angle in degrees
     */
    @SuppressWarnings("unused")
    public void changeAngle(int angle) {
        this.drawable.changeAngle(angle);
    }
}
