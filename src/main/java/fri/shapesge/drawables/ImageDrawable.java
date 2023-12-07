package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class ImageDrawable extends TranslatableDrawable {
    private BufferedImage image;

    public ImageDrawable(int x, int y, int angle, BufferedImage image) {
        super(x, y, angle);
        this.image = image;
    }

    public void changeImage(BufferedImage image) {
        this.image = image;

        Game.getGame().somethingHasChanged();
    }

    @Override
    protected Point2D.Double getCenter() {
        double centerX = this.image.getWidth() / 2.0;
        double centerY = this.image.getHeight() / 2.0;
        return new Point2D.Double(centerX, centerY);
    }

    @Override
    public void draw(Graphics2D canvas) {
        if (!this.isVisible()) {
            return;
        }

        canvas.drawImage(this.image, this.getTransform(), null);
    }
}
