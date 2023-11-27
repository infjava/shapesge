package fri.shapesge;

import fri.shapesge.drawables.TextDrawable;

import java.awt.Color;
import java.awt.Font;
import java.util.EnumSet;

/**
 * A text that can be manipulated and that draws itself on a canvas.
 *
 * @author Ján Janech
 * @version 1.0  (25.11.2022)
 */

@SuppressWarnings("unused")
public class TextBlock {
    private final TextDrawable drawable;

    /**
     * Create a new text at a given position with default color.
     * @param text text to be displayed
     * @param x x-coordinate of the text
     *          (distance from left border of the canvas)
     * @param y y-coordinate of the text
     *          (distance from top border of the canvas)
     */
    @SuppressWarnings("unused")
    public TextBlock(String text, int x, int y) {
        this.drawable = new TextDrawable(x, y, Color.black, text, new Font(Font.SERIF, Font.PLAIN, 12));
    }

    /**
     * Create a new text at default position with default color.
     * @param text text to be displayed
     */
    @SuppressWarnings("unused")
    public TextBlock(String text) {
        this(text, 0, 20);
    }

    /**
     * Make this text visible. If it was already visible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeVisible() {
        this.drawable.makeVisible();
    }

    /**
     * Make this text invisible. If it was already invisible, do nothing.
     */
    @SuppressWarnings("unused")
    public void makeInvisible() {
        this.drawable.makeInvisible();
    }

    /**
     * Move the text a few pixels to the right.
     */
    @SuppressWarnings("unused")
    public void moveRight() {
        this.drawable.moveBy(20, 0);
    }

    /**
     * Move the text a few pixels to the left.
     */
    @SuppressWarnings("unused")
    public void moveLeft() {
        this.drawable.moveBy(-20, 0);
    }

    /**
     * Move the text a few pixels up.
     */
    @SuppressWarnings("unused")
    public void moveUp() {
        this.drawable.moveBy(0, -20);
    }

    /**
     * Move the text a few pixels down.
     */
    @SuppressWarnings("unused")
    public void moveDown() {
        this.drawable.moveBy(0, 20);
    }

    /**
     * Move the text horizontally by 'distance' pixels.
     * @param distance distance in pixels
     */
    @SuppressWarnings("unused")
    public void moveHorizontal(int distance) {
        this.drawable.moveBy(distance, 0);
    }

    /**
     * Move the text vertically by 'distance' pixels.
     * @param distance distance in pixels
     */
    @SuppressWarnings("unused")
    public void moveVertical(int distance) {
        this.drawable.moveBy(0, distance);
    }

    /**
     * Change the font according to the new specification.
     * @param fontFamily name of the font family
     *                   (e.g. "Arial", "Times New Roman", "Courier New")
     * @param style      style of the font
     *                   (e.g. {@link FontStyle#BOLD}, {@link FontStyle#ITALIC}, {@link FontStyle#UNDERLINE})
     * @param size       size of the font in pixels
     */
    @SuppressWarnings("unused")
    public void changeFont(String fontFamily, FontStyle style, int size) {
        this.drawable.changeFont(fontFamily, style == FontStyle.BOLD, style == FontStyle.ITALIC, style == FontStyle.UNDERLINE, size);
    }

    /**
     * Change the font according to the new specification.
     * @param fontFamily name of the font family
     *                   (e.g. "Arial", "Times New Roman", "Courier New")
     * @param style      style of the font
     *                   (e.g. {@code EnumSet.of(FontStyle.BOLD, FontStyle.ITALIC)})
     * @param size       size of the font in pixels
     */
    @SuppressWarnings("unused")
    public void changeFont(String fontFamily, EnumSet<FontStyle> style, int size) {
        this.drawable.changeFont(fontFamily, style.contains(FontStyle.BOLD), style.contains(FontStyle.ITALIC), style.contains(FontStyle.UNDERLINE), size);
    }

    /**
     * Change the color.
     * @param newColor new color from palette or in #rrggbb format
     */
    @SuppressWarnings("unused")
    public void changeColor(String newColor) {
        this.drawable.changeColor(newColor);
    }

    /**
     * Change the text.
     * @param text new text
     */
    @SuppressWarnings("unused")
    public void changeText(String text) {
        this.drawable.changeText(text);
    }
}
