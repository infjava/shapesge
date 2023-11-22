package fri.shapesge.drawables;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.util.HashMap;

public class TextDrawable extends FilledDrawable {
    private String[] text;
    private Font font;

    public TextDrawable(int x, int y, Color color, String text, Font font) {
        super(x, y, color);

        this.text = text.split("\n");
        this.font = font;
    }

    public void changeFont(String fontFamily, boolean bold, boolean italic, boolean underline, int size) {
        var intStyle = Font.PLAIN;

        if (bold) {
            intStyle |= Font.BOLD;
        }

        if (italic) {
            intStyle |= Font.ITALIC;
        }

        var newFont = new Font(fontFamily, intStyle, size);

        if (underline) {
            var underlineAttribute = new HashMap<TextAttribute, Integer>();
            underlineAttribute.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            this.font = newFont.deriveFont(underlineAttribute);
        } else {
            this.font = newFont;
        }
    }

    public void changeText(String text) {
        this.text = text.split("\n");
    }

    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(this.getColor());

        canvas.setFont(this.font);
        var y = this.getYPosition();
        final var lineHeight = canvas.getFontMetrics().getHeight();

        for (String line : this.text) {
            canvas.drawString(line, this.getXPosition(), y);
            y += lineHeight;
        }
    }
}
