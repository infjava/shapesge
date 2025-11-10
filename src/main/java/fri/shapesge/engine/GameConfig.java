package fri.shapesge.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

class GameConfig {
    public static final String WINDOW_SECTION = "Window";
    public static final String WINDOW_TITLE = "Title";
    public static final String WINDOW_WIDTH = "Width";
    public static final String WINDOW_HEIGHT = "Height";
    public static final String CANVAS_BACKGROUND = "Background";
    public static final String FPS = "FPS";
    public static final String SHOW_INFO = "ShowInfo";
    public static final String FULLSCREEN = "Fullscreen";
    public static final String EXIT_ON_CLOSE = "ExitOnClose";
    public static final String ON_CLOSE = "OnClose";

    public static final String SHAPES_SECTION = "Shapes";
    public static final String IMAGE_SOURCE = "ImageSource";

    public static final String KEYBOARD_SECTION = "Keyboard";

    public static final String MOUSE_SECTION = "Mouse";

    public static final String TIMER_SECTION = "Timers";
    public static final String COLORS_SECTION = "Colors";

    public static final String ASSETS_SECTION = "Assets";
    public static final String ASSET_SOURCE = "AssetSource";
    public static final String ASSET_SOURCE_RESOURCE = "resource";
    public static final String ASSET_SOURCE_FILE = "file";

    private final ArrayList<GameConfigFile> configFiles;

    GameConfig() {
        this.configFiles = new ArrayList<>();

        var appConfigStream = ClassLoader.getSystemResourceAsStream("sbge.ini");
        if (appConfigStream != null) {
            this.configFiles.add(new GameConfigFile(appConfigStream));
        } else {
            var localConfig = new File("sbge.ini");
            try {
                var localConfigStream = new FileInputStream(localConfig);
                this.configFiles.add(new GameConfigFile(localConfigStream));
            } catch (IOException e) {
                // do nothing
            }
        }

        var defaultConfigStream = GameConfig.class.getResourceAsStream("sbge-defaults.ini");
        if (defaultConfigStream == null) {
            throw new RuntimeException("Internal SPGE error - missing config");
        }
        this.configFiles.add(new GameConfigFile(defaultConfigStream));
    }

    public boolean contains(String section, String option) {
        for (GameConfigFile configFile : this.configFiles) {
            if (configFile.contains(section, option)) {
                return true;
            }
        }

        return false;
    }

    public String get(String section, String option) {
        for (GameConfigFile configFile : this.configFiles) {
            if (configFile.contains(section, option)) {
                return configFile.get(section, option);
            }
        }

        throw new RuntimeException(String.format("Config %s/%s missing", section, option));
    }

    public int getInt(String section, String option) {
        for (GameConfigFile configFile : this.configFiles) {
            if (configFile.contains(section, option)) {
                return configFile.getInt(section, option);
            }
        }

        throw new RuntimeException(String.format("Config %s/%s missing", section, option));
    }

    public boolean getBoolean(String section, String option) {
        for (GameConfigFile configFile : this.configFiles) {
            if (configFile.contains(section, option)) {
                return configFile.getBoolean(section, option);
            }
        }

        throw new RuntimeException(String.format("Config %s/%s missing", section, option));
    }

    public Iterable<GameConfigOption> getOptions(String section) {
        for (GameConfigFile configFile : this.configFiles) {
            if (configFile.contains(section)) {
                return configFile.getOptions(section);
            }
        }

        throw new RuntimeException(String.format("Config section %s missing", section));
    }
}
