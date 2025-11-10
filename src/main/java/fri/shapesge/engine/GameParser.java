package fri.shapesge.engine;

import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GameParser {
    private enum AssetSource {
        FILE,
        RESOURCE
    }

    private static final GraphicsConfiguration DEFAULT_GRAPHICS_CONFIGURATION = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();

    private static final ColorModel DEFAULT_COLOR_MODEL = DEFAULT_GRAPHICS_CONFIGURATION.getColorModel();

    private static final Map<String, Integer> KEY_MAP = Arrays.stream(KeyEvent.class
            .getDeclaredFields())
            .filter(x -> Modifier.isStatic(x.getModifiers()) && Modifier.isFinal(x.getModifiers()))
            .filter(x -> x.getName().startsWith("VK_"))
            .collect(
                    Collectors.toMap(
                            x -> x.getName().substring(3).toLowerCase(),
                            x -> {
                                try {
                                    return (Integer)x.get(null);
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

    private final HashMap<String, Color> colorMap;
    private final AssetSource assetSource;

    GameParser(GameConfig gameConfig) {
        this.colorMap = new HashMap<>();
        for (var color : gameConfig.getOptions(GameConfig.COLORS_SECTION)) {
            this.colorMap.put(color.getOption(), Color.decode(color.getValue()));
        }

        String assetSourceValue;

        if (gameConfig.contains(GameConfig.SHAPES_SECTION, GameConfig.IMAGE_SOURCE)) {
            System.out.format(
                "ShapesGE: Using deprecated [%s]/%s setting, use [%s]/%s instead",
                    GameConfig.SHAPES_SECTION,
                    GameConfig.IMAGE_SOURCE,
                    GameConfig.ASSETS_SECTION,
                    GameConfig.ASSET_SOURCE
            );

            assetSourceValue = gameConfig.get(GameConfig.SHAPES_SECTION, GameConfig.IMAGE_SOURCE);
        } else {
            assetSourceValue = gameConfig.get(GameConfig.ASSETS_SECTION, GameConfig.ASSET_SOURCE);
        }

        switch (assetSourceValue.toLowerCase()) {
            case GameConfig.ASSET_SOURCE_RESOURCE:
                this.assetSource = AssetSource.RESOURCE;
                break;
            case GameConfig.ASSET_SOURCE_FILE:
                this.assetSource = AssetSource.FILE;
                break;
            default:
                throw new RuntimeException("Invalid image source");
        }
    }

    public GameOnCloseOperation parseOnClose(String onCloseOperation) {
        var operationTypeAndMessage = onCloseOperation.strip().split("\\p{javaWhitespace}+", 2);

        switch (operationTypeAndMessage[0]) {
            case "hide":
                if (operationTypeAndMessage.length > 1) {
                    throw new RuntimeException("Invalid on close operation");
                }
                return new GameOnCloseOperation(GameOnCloseOperationType.HIDE, null);
            case "exit":
                if (operationTypeAndMessage.length > 1) {
                    throw new RuntimeException("Invalid on close operation");
                }
                return new GameOnCloseOperation(GameOnCloseOperationType.EXIT, null);
            case "nothing":
                if (operationTypeAndMessage.length > 1) {
                    throw new RuntimeException("Invalid on close operation");
                }
                return new GameOnCloseOperation(GameOnCloseOperationType.DO_NOTHING, null);
            case "send":
                if (operationTypeAndMessage.length < 2) {
                    throw new RuntimeException("Invalid on close operation");
                }
                return new GameOnCloseOperation(GameOnCloseOperationType.SEND_MESSAGE, operationTypeAndMessage[1]);
            default:
                throw new RuntimeException("Invalid on close operation");
        }
    }

    public Color parseColor(String colorString) {
        if (colorString.startsWith("#")) {
            return Color.decode(colorString);
        }

        var ret = this.colorMap.get(colorString);
        if (ret == null) {
            return Color.black;
        }

        return ret;
    }

    public BufferedImage parseImage(String imagePath) {
        BufferedImage loadedImage = null;

        try {
            switch (this.assetSource) {
                case FILE:
                    loadedImage = ImageIO.read(new File(imagePath));
                    break;
                case RESOURCE:
                    InputStream resource = ClassLoader.getSystemResourceAsStream(imagePath);
                    if (resource != null) {
                        loadedImage = ImageIO.read(resource);
                    }
                    break;
            }
        } catch (IOException ignored) {
        }

        if (loadedImage == null) {
            throw new ShapesGEException("File " + imagePath + " was not found.");
        } else {
            return GameParser.toCompatibleImage(loadedImage);
        }
    }

    /**
     * Converts the image to a compatible image,
     * inspired by <a href="https://stackoverflow.com/a/19659301">https://stackoverflow.com/a/19659301</a>
     */
    private static BufferedImage toCompatibleImage(BufferedImage image) {
        if (image.getColorModel().equals(DEFAULT_COLOR_MODEL)) {
            return image;
        }

        var compatibleImage = DEFAULT_GRAPHICS_CONFIGURATION.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

        var g2d = compatibleImage.createGraphics();
        try {
            g2d.drawImage(image, 0, 0, null);
        } finally {
            g2d.dispose();
        }

        return compatibleImage;
    }

    public AudioInputStream parseWaveAudio(String audioPath) {
        AudioInputStream loadedAudio = null;

        try {
            switch (this.assetSource) {
                case FILE:
                    loadedAudio = AudioSystem.getAudioInputStream(new File(audioPath));
                    break;
                case RESOURCE:
                    InputStream resource = ClassLoader.getSystemResourceAsStream(audioPath);
                    if (resource != null) {
                        loadedAudio = AudioSystem.getAudioInputStream(resource);
                    }
                    break;
            }
        } catch (IOException | UnsupportedAudioFileException ignored) {
        }

        if (loadedAudio == null) {
            throw new ShapesGEException("File " + audioPath + " was not found.");
        } else {
            return loadedAudio;
        }
    }

    public Sequence parseMidiAudio(String audioPath) {
        Sequence loadedAudio = null;

        try {
            switch (this.assetSource) {
                case FILE:
                    loadedAudio = MidiSystem.getSequence(new File(audioPath));
                    break;
                case RESOURCE:
                    InputStream resource = ClassLoader.getSystemResourceAsStream(audioPath);
                    if (resource != null) {
                        loadedAudio = MidiSystem.getSequence(resource);
                    }
                    break;
            }
        } catch (IOException | InvalidMidiDataException ignored) {
        }

        if (loadedAudio == null) {
            throw new ShapesGEException("File " + audioPath + " was not found.");
        } else {
            return loadedAudio;
        }
    }

    GameKeyEvent parseKeyEvent(String keyEvent, String message) {
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

        var keyShortcut = eventTypeAndKey[1].split("\\\\+");
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

    GameMouseEvent parseMouseEvent(String mouseEvent, String message) {
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
