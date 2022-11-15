package fri.shapesge;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

class GameParser {
    private static final Color BROWN = new Color(102, 51, 0);

    private static final Map<String, Integer> KEY_MAP = Arrays.stream(KeyEvent.class
            .getDeclaredFields())
            .filter(x -> Modifier.isStatic(x.getModifiers()) && Modifier.isFinal(x.getModifiers()))
            .filter(x -> x.getName().startsWith("VK_"))
            .collect(
                    Collectors.toMap(
                            x -> x.getName().substring(3).toLowerCase(),
                            x -> {
                                try {
                                    return (Integer) x.get(null);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    )
            );

    private static final Map<String, Integer> MOUSE_BUTTON_MAP = Map.of(
            "left", MouseEvent.BUTTON1,
            "right", MouseEvent.BUTTON2,
            "middle", MouseEvent.BUTTON3
    );

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
                return GameParser.BROWN;
            default:
                return Color.black;
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

    public static GameKeyEvent parseKeyEvent(String keyEvent, String message) {
        var eventTypeAndKey = keyEvent.strip().split("\\p{javaWhitespace}+", 2);
        if (eventTypeAndKey.length != 2) {
            throw new RuntimeException(String.format("Cannot parse key event %s", keyEvent));
        }

        int eventType;
        switch (eventTypeAndKey[0]) {
            case "pressed":
                eventType = KeyEvent.KEY_PRESSED;
                break;
            case "released":
                eventType = KeyEvent.KEY_RELEASED;
                break;
            case "typed":
                eventType = KeyEvent.KEY_TYPED;
                break;
            default:
                throw new RuntimeException(String.format("Cannot parse key event %s", keyEvent));
        }

        var keyShortcut = eventTypeAndKey[1].split("\\+");
        int modifiers = 0;
        for (int i = 0; i < keyShortcut.length - 1; i++) {
            switch (keyShortcut[i].toLowerCase()) {
                case "shift":
                    modifiers |= KeyEvent.SHIFT_DOWN_MASK;
                    break;
                case "ctrl":
                    modifiers |= KeyEvent.CTRL_DOWN_MASK;
                    break;
                case "alt":
                    modifiers |= KeyEvent.ALT_DOWN_MASK;
                    break;
                case "win":
                case "meta":
                    modifiers |= KeyEvent.META_DOWN_MASK;
                    break;
                default:
                    throw new RuntimeException(String.format("Cannot parse key event %s", keyEvent));
            }
        }

        final var keyName = keyShortcut[keyShortcut.length - 1].toLowerCase();
        final var keyCode = KEY_MAP.get(keyName);
        if (keyCode == null) {
            throw new RuntimeException(String.format("Cannot parse key event %s", keyEvent));
        }

        return new GameKeyEvent(eventType, modifiers, keyCode, message);
    }

    public static GameMouseEvent parseMouseEvent(String mouseEvent, String message) {
        var eventTypeAndButton = mouseEvent.strip().split("\\p{javaWhitespace}+", 2);

        if (eventTypeAndButton[0].equals("move") && eventTypeAndButton.length == 1) {
            return new GameMouseEvent(MouseEvent.MOUSE_MOVED, 0, message);
        } else {
            if (eventTypeAndButton.length != 2) {
                throw new RuntimeException(String.format("Cannot parse mouse event %s", mouseEvent));
            }

            int eventType;
            switch (eventTypeAndButton[0]) {
                case "pressed":
                    eventType = MouseEvent.MOUSE_PRESSED;
                    break;
                case "released":
                    eventType = MouseEvent.MOUSE_RELEASED;
                    break;
                case "clicked":
                    eventType = MouseEvent.MOUSE_CLICKED;
                    break;
                default:
                    throw new RuntimeException(String.format("Cannot parse mouse event %s", mouseEvent));
            }

            final var buttonName = eventTypeAndButton[1].toLowerCase();
            if (buttonName.startsWith("button")) {
                var button = Integer.parseInt(buttonName.substring(6));
                return new GameMouseEvent(eventType, button, message);
            } else {
                var button = MOUSE_BUTTON_MAP.get(buttonName);
                if (button == null) {
                    throw new RuntimeException(String.format("Cannot parse mouse event %s", mouseEvent));
                }
                return new GameMouseEvent(eventType, button, message);
            }
        }
    }
}
