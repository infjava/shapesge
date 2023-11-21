package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.GameDrawable;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * The Image class represents a bitmap image that can be drawn on the canvas.
 *
 * @author original: Miroslav Kvaššay and Michal Varga
 * @author engine: Ján Janech
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Image {
    private static final AffineTransform IDENTITY_TRANSFORM = new AffineTransform();

    private final ImageDrawable drawable;
    private int xPosition;
    private int yPosition;
    private int angle;
    private AffineTransform transform;
    private BufferedImage image;
    private boolean isVisible;

    /**
     * Create a new image at default position with default color.
     */
    @SuppressWarnings("unused")
    public Image(String imagePath) {
        this(imagePath, 100, 100);
    }

    @SuppressWarnings("unused")
    public Image(String imagePath, int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.angle = 0;

        this.computeTransformation();

        this.image = Game.getGame().getParser().parseImage(imagePath);
        this.isVisible = false;

        this.drawable = new ImageDrawable();
    }

    /**
     * Make this image visible. If it was already visible, do nothing.
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
     * Make this image invisible. If it was already invisible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeInvisible() {
        if (!this.isVisible) {
            return;
        }

        Game.getGame().unregisterDrawable(this.drawable);
        this.isVisible = false;
    }

    /**
     * Move the image a few pixels to the right.
     */
    @SuppressWarnings("unused")
    public void moveRight() {
        this.moveHorizontal(20);
    }

    /**
     * Move the image a few pixels to the left.
     */
    @SuppressWarnings("unused")
    public void moveLeft() {
        this.moveHorizontal(-20);
    }

    /**
     * Move the image a few pixels up.
     */
    @SuppressWarnings("unused")
    public void moveUp() {
        this.moveVertical(-20);
    }

    /**
     * Move the image a few pixels down.
     */
    @SuppressWarnings("unused")
    public void moveDown() {
        this.moveVertical(20);
    }

    /**
     * Move the image horizontally by 'distance' pixels.
     */
    @SuppressWarnings("unused")
    public void moveHorizontal(int distance) {
        this.xPosition += distance;
        this.computeTransformation();
    }

    /**
     * Move the image vertically by 'distance' pixels.
     */
    @SuppressWarnings("unused")
    public void moveVertical(int distance) {
        this.yPosition += distance;
        this.computeTransformation();
    }

    /**
     * Change the drawn image. Image must exist.
     */
    @SuppressWarnings("unused")
    public void changeImage(String imagePath) {
        this.image = Game.getGame().getParser().parseImage(imagePath);
    }

    /**
     * Change the image rotation angle according to the parameter. North = 0.
     */
    @SuppressWarnings("unused")
    public void changeAngle(int angle) {
        this.angle = angle;
        this.computeTransformation();
    }

    private void computeTransformation() {
        if (this.angle == 0 && this.xPosition == 0 && this.yPosition == 0) {
            this.transform = IDENTITY_TRANSFORM;
        } else if (this.angle == 0) {
            this.transform = AffineTransform.getTranslateInstance(this.xPosition, this.yPosition);
        } else {
            var centerX = this.image.getWidth() / 2.0;
            var centerY = this.image.getHeight() / 2.0;
            var transformation = new AffineTransform();
            transformation.translate(this.xPosition + centerX, this.yPosition + centerY);
            transformation.rotate(Math.toRadians(this.angle));
            transformation.translate(-centerX, -centerY);
            this.transform = transformation;
        }
    }

    private class ImageDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Image.this.isVisible) {
                return;
            }

            canvas.drawImage(Image.this.image, Image.this.transform, null);
        }
    }
}
