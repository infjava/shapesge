package fri.shapesge;

import java.util.ArrayList;
import java.util.function.Consumer;

class GameObjects {
    private final ArrayList<DrawableShape> shapes;

    public GameObjects() {
        this.shapes = new ArrayList<>();
    }

    public synchronized void registerShape(DrawableShape drawableShape) {
        this.shapes.add(drawableShape);
    }

    public synchronized void forEachShape(Consumer<DrawableShape> consumer) {
        this.shapes.forEach(consumer);
    }
}
