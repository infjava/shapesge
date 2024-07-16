package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TextDrawable extends FilledDrawable {
    private String[] text;
    private Font font;

    private int lineSpacing;

    public TextDrawable(int x, int y, Color color, String text, Font font) {
        super(x, y, color);

        this.text = text.split("\n");
        this.font = font;

        this.lineSpacing = 0;
    }

    public void changeFont(String fontFamily, boolean bold, boolean italic, boolean underline, int size, int lineSpacing) {
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
        var metrics = canvas.getFontMetrics();
        var y = this.getYPosition() + metrics.getAscent();
        final var lineHeight = metrics.getHeight();

        for (String line : this.text) {
            canvas.drawString(line, this.getXPosition(), y);
            y += lineHeight + this.lineSpacing;
        }
    }

    public int getLineSpacing() {
        return this.lineSpacing;
    }

    private FontMetrics generateFontMetrics() {
        Graphics2D g2d;
        g2d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        g2d.setFont(this.font);

        return g2d.getFontMetrics();
    }

    public int getWidth() {
        var fontMetrics = this.generateFontMetrics();
        int maxWidth = 0;
        for (String line : this.text) {
            maxWidth = Math.max(maxWidth, fontMetrics.stringWidth(line));
        }
        return maxWidth;
    }

    public int getHeight() {
        var fontMetrics = this.generateFontMetrics();
        return ((fontMetrics.getHeight() + this.lineSpacing) * this.text.length) - this.lineSpacing;
    }
}
