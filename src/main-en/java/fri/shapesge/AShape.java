package fri.shapesge;

public interface AShape {
//    void makeInvisible();
//    void makeVisible();
//    void moveLeft();
//    void moveRight();
//    void moveUp();
//    void moveDown();
//    void moveVertical(int distance);
//    void moveHorizontal(int distance);
//    void changePosition(int x, int y);

    /**
     * @return the x-coordinate (offset from left border) of the shape.
     */
    int getPositionX();

    /**
     * @return the y-coordinate (offset from top border) of the shape.
     */
    int getPositionY();

    /**
     * @return the width of the shape.
     */
    int getWidth();

    /**
     * @return the height of the shape.
     */
    int getHeight();
}
