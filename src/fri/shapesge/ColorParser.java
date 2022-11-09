package fri.shapesge;

import java.awt.Color;

class ColorParser {
    private static final Color BROWN = new Color(102, 51, 0);
    public static Color parse(String colorString) {
        if (colorString.startsWith("#")) {
            return Color.decode(colorString);
        }

        switch (colorString) {
            case "red":
                return Color.red;
            case "blue":
                return Color.blue;
            case "yellow":
                return Color.yellow;
            case "green":
                return Color.green;
            case "magenta":
                return Color.magenta;
            case "white":
                return Color.white;
            case "brown":
                return ColorParser.BROWN;
            default:
                return Color.black;
        }
    }
}
