package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class TranslatableDrawable extends GameDrawable {
    private static final AffineTransform IDENTITY_TRANSFORM = new AffineTransform();

    private int angle;

    private AffineTransform transform;

    public TranslatableDrawable(int x, int y, int angle) {
        super(x, y);

        this.angle = angle;

        this.computeTransformation();
    }

    @Override
    public void moveTo(int newX, int newY) {
        super.moveTo(newX, newY);

        this.computeTransformation();
    }

    @Override
    public void moveBy(int dx, int dy) {
        super.moveBy(dx, dy);

        this.computeTransformation();
    }

    public void changeAngle(int angle) {
        this.angle = angle;

        this.computeTransformation();

        Game.getGame().somethingHasChanged();
    }

    private void computeTransformation() {
        if (this.angle == 0 && this.getXPosition() == 0 && this.getYPosition() == 0) {
            this.transform = IDENTITY_TRANSFORM;
        } else if (this.angle == 0) {
            this.transform = AffineTransform.getTranslateInstance(this.getXPosition(), this.getYPosition());
        } else {
            var center = this.getCenter();
            var transformation = new AffineTransform();
            transformation.translate(this.getXPosition() + center.getX(), this.getYPosition() + center.getY());
            transformation.rotate(Math.toRadians(this.angle));
            transformation.translate(-center.getX(), -center.getY());
            this.transform = transformation;
        }
    }

    protected AffineTransform getTransform() {
        return this.transform;
    }

    protected abstract Point2D.Double getCenter();
}
