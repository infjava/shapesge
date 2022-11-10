package fri.shapesge;

import java.awt.Graphics2D;
import java.util.ArrayList;

class GameObjects {
    private final ArrayList<DrawableShape> shapes;

    public GameObjects() {
        this.shapes = new ArrayList<>();
    }

    public synchronized void registerShape(DrawableShape drawableShape) {
        this.shapes.add(drawableShape);
    }

    public synchronized void drawAll(Graphics2D canvas) {
        for (DrawableShape shape : this.shapes) {
            shape.draw(canvas);
        }
    }
}
