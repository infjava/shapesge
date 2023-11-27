package fri.shapesge;

import fri.shapesge.engine.Game;

import java.awt.image.BufferedImage;

/**
 * The ImageData class represents a bitmap image that can be drawn using the {@link Image} class.
 *
 * @author Ján Janech
 * @version 1.0
 */
public class ImageData {

    private final BufferedImage image;

    /**
     * Reads an image from a given path.
     * @param imagePath path to the image
     */
    public ImageData(String imagePath) {
        this.image = Game.getGame().getParser().parseImage(imagePath);
    }

    /**
     * Returns width of the image.
     * @return width of the image
     */
    @SuppressWarnings("unused")
    public int getWidth() {
        return this.image.getWidth();
    }

    /**
     * Returns height of the image.
     * @return height of the image
     */
    @SuppressWarnings("unused")
    public int getHeight() {
        return this.image.getHeight();
    }

    BufferedImage getImage() {
        return this.image;
    }
}
