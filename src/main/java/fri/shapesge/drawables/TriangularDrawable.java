package fri.shapesge.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class TriangularDrawable extends FilledDrawable {
    private int width;
    private int height;

    public TriangularDrawable(int x, int y, int width, int height, Color color) {
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

        var xPoints = new int[]{this.xPosition, this.xPosition + (this.width / 2), this.xPosition - this.width / 2};
        var yPoints = new int[]{this.yPosition, this.yPosition + this.height, this.yPosition + this.height};
        var shape = new Polygon(xPoints, yPoints, 3);
        canvas.setColor(this.color);
        canvas.fill(shape);
    }
}
