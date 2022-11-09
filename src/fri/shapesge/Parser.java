package fri.shapesge;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Parser {
    private static final AffineTransform IDENTITY_TRANSFORM = new AffineTransform();

    private static final Color BROWN = new Color(102, 51, 0);

    public static Color parseColor(String colorString) {
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
                return Parser.BROWN;
            default:
                return Color.black;
        }
    }

    public static AffineTransform parseAngle(int angle) {
        if (angle == 0) {
            return Parser.IDENTITY_TRANSFORM;
        } else {
            return AffineTransform.getRotateInstance(Math.toRadians(angle));
        }
    }

    public static BufferedImage parseImage(String imagePath){
        BufferedImage loadedImage;

        try {
            loadedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            loadedImage = null;
            javax.swing.JOptionPane.showMessageDialog(null, "File " + imagePath + " was not found.");
        }

        return loadedImage;
    }
}
