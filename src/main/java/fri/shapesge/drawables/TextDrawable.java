package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;

public class TextDrawable extends FilledDrawable {
    private String[] text;
    private Font font;

    private FontMetrics lastUsedMetrics;
    private int lineSpacing;

    public TextDrawable(int x, int y, Color color, String text, Font font) {
        super(x, y, color);

        this.text = text.split("\n");
        this.font = font;

        this.lineSpacing = 0;
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

        this.lineSpacing = lineSpacing;

        Game.getGame().somethingHasChanged();
    }

    public void changeText(String text) {
        this.text = text.split("\n");

        Game.getGame().somethingHasChanged();
    }

    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(this.getColor());

        canvas.setFont(this.font);
        this.lastUsedMetrics = canvas.getFontMetrics();
        var y = this.getYPosition() + this.lastUsedMetrics.getAscent();
        final var lineHeight = this.lastUsedMetrics.getHeight();

        for (String line : this.text) {
            canvas.drawString(line, this.getXPosition(), y);
            y += lineHeight + this.lineSpacing;
        }
    }

    public FontMetrics getLastUsedFontMetrics() {
        return this.lastUsedMetrics;
    }
}
