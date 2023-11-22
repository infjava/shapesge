package fri.shapesge.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class RectangularDrawable extends FilledDrawable {
    private int width;
    private int height;

    public RectangularDrawable(int x, int y, int width, int height, Color color) {
        super(x, y, color);

        this.width = width;
        this.height = height;
    }

    public void changeSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D canvas) {
        if (!this.isVisible) {
            return;
        }

        var shape = new Rectangle2D.Double(this.xPosition, this.yPosition, this.width, this.height);
        canvas.setColor(this.color);
        canvas.fill(shape);
    }
}
