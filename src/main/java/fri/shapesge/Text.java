package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.GameDrawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * A text that can be manipulated and that draws itself on a canvas.
 *
 * @author Ján Janech
 * @version 1.0  (25.11.2022)
 */

@SuppressWarnings("unused")
public class Text {
    private final TextDrawable drawable;
    private String[] text;
    private Font font;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;

    /**
     * Create a new text at default position with default color.
     */
    @SuppressWarnings("unused")
    public Text(String text, int x, int y) {
        this.text = text.split("\n");

        this.font = new Font(Font.SERIF, Font.PLAIN, 12);

        this.xPosition = x;
        this.yPosition = y;
        this.color = Color.black;
        this.isVisible = false;

        this.drawable = new TextDrawable();
    }

    @SuppressWarnings("unused")
    public Text(String text) {
        this(text, 0, 20);
    }

    /**
     * Make this text visible. If it was already visible, do nothing.
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
     * Make this text invisible. If it was already invisible, do nothing.
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
     * Move the text a few pixels to the right.
     */
    @SuppressWarnings("unused")
    public void moveRight() {
        this.moveHorizontal(20);
    }

    /**
     * Move the text a few pixels to the left.
     */
    @SuppressWarnings("unused")
    public void moveLeft() {
        this.moveHorizontal(-20);
    }

    /**
     * Move the text a few pixels up.
     */
    @SuppressWarnings("unused")
    public void moveUp() {
        this.moveVertical(-20);
    }

    /**
     * Move the text a few pixels down.
     */
    @SuppressWarnings("unused")
    public void moveDown() {
        this.moveVertical(20);
    }

    /**
     * Move the text horizontally by 'distance' pixels.
     */
    @SuppressWarnings("unused")
    public void moveHorizontal(int distance) {
        this.xPosition += distance;
    }

    /**
     * Move the text vertically by 'distance' pixels.
     */
    @SuppressWarnings("unused")
    public void moveVertical(int distance) {
        this.yPosition += distance;
    }

    /**
     * Change the font according to the new specification.
     */
    @SuppressWarnings("unused")
    public void changeFont(String fontFamily, FontStyle style, int size) {
        var intStyle = Font.PLAIN;

        if (style == FontStyle.BOLD) {
            intStyle |= Font.BOLD;
        }

        if (style == FontStyle.ITALIC) {
            intStyle |= Font.ITALIC;
        }

        var newFont = new Font(fontFamily, intStyle, size);

        if (style == FontStyle.UNDERLINE) {
            var underlineAttribute = new HashMap<TextAttribute, Integer>();
            underlineAttribute.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            this.font = newFont.deriveFont(underlineAttribute);
        } else {
            this.font = newFont;
        }
    }

    /**
     * Change the font according to the new specification.
     */
    @SuppressWarnings("unused")
    public void changeFont(String fontFamily, EnumSet<FontStyle> style, int size) {
        var intStyle = Font.PLAIN;

        if (style.contains(FontStyle.BOLD)) {
            intStyle |= Font.BOLD;
        }

        if (style.contains(FontStyle.ITALIC)) {
            intStyle |= Font.ITALIC;
        }

        var newFont = new Font(fontFamily, intStyle, size);

        if (style.contains(FontStyle.UNDERLINE)) {
            var underlineAttribute = new HashMap<TextAttribute, Integer>();
            underlineAttribute.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            this.font = newFont.deriveFont(underlineAttribute);
        } else {
            this.font = newFont;
        }
    }

    /**
     * Change the color.
     */
    @SuppressWarnings("unused")
    public void changeColor(String newColor) {
        this.color = Game.getGame().getParser().parseColor(newColor);
    }

    /**
     * Change the text.
     */
    @SuppressWarnings("unused")
    public void changeText(String text) {
        this.text = text.split("\n");
    }

    private class TextDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            canvas.setColor(Text.this.color);

            canvas.setFont(Text.this.font);
            var y = Text.this.yPosition;
            final var lineHeight = canvas.getFontMetrics().getHeight();

            for (String line : Text.this.text) {
                canvas.drawString(line, Text.this.xPosition, y);
                y += lineHeight;
            }
        }
    }
}
