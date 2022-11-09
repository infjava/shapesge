package fri.shapesge;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Image class represents a bitmap image that can be drawn on the canvas.
 *
 * @author original: Miroslav Kvaššay and Michal Varga
 * @author engine: Ján Janech
 * @version 1.0
 */
public class Image {
    private int xPosition;
    private int yPosition;
    private int angle;
    private BufferedImage image;
    private boolean isVisible;

    /**
     * Create a new image at default position with default color.
     */
    public Image(String imagePath) {
        this.xPosition = 100;
        this.yPosition = 100;
        this.image = this.loadImageFromFile(imagePath);
        this.angle = 0;
        this.isVisible = false;
    }

    /**
     * Make this image visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        this.isVisible = true;
    }

    /**
     * Make this image invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        this.isVisible = false;
    }

    /**
     * Move the image a few pixels to the right.
     */
    public void moveRight() {
        this.moveHorizontal(20);
    }

    /**
     * Move the image a few pixels to the left.
     */
    public void moveLeft() {
        this.moveHorizontal(-20);
    }

    /**
     * Move the image a few pixels up.
     */
    public void moveUp() {
        this.moveVertical(-20);
    }

    /**
     * Move the image a few pixels down.
     */
    public void moveDown() {
        this.moveVertical(20);
    }

    /**
     * Move the image horizontally by 'distance' pixels.
     */
    public void moveHorizontal(int distance) {
        this.xPosition += distance;
    }

    /**
     * Move the image vertically by 'distance' pixels.
     */
    public void moveVertical(int distance) {
        this.yPosition += distance;
    }

    /**
     * Change the drawn image. Image must exist.
     */
    public void changeImage(String imagePath) {
        this.image = this.loadImageFromFile(imagePath);
    }

    /**
     * Change the image rotation angle according to the parameter. North = 0.
     */
    public void changeAngle(int angle) {
        this.angle = angle;
    }

    private BufferedImage loadImageFromFile(String imagePath) {
        BufferedImage loadedImage;

        try {
            loadedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            loadedImage = null;
            javax.swing.JOptionPane.showMessageDialog(null, "File " + imagePath + " was not found.");
        }

        return loadedImage;
    }
}
