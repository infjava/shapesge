package fri.shapesge;

import java.awt.Color;

class GameConfig {
    public static final String WINDOW_SECTION = "Window";
    public static final String WINDOW_TITLE = "Title";
    public static final String WINDOW_WIDTH = "Width";
    public static final String WINDOW_HEIGHT = "Height";
    public static final String CANVAS_BACKGROUND = "Background";
    public static final String FPS = "FPS";
    public static final String SHOW_INFO = "ShowInfo";
    public static final String FULLSCREEN = "Fullscreen";

    public static final String KEYBOARD_SECTION = "Keyboard";

    public static final String MOUSE_SECTION = "Mouse";

    public static final String TIMER_SECTION = "Timers";

    private final GameConfigFile appConfig;
    private final GameConfigFile defaultConfig;

    public GameConfig() {
        var appConfigStream = ClassLoader.getSystemResourceAsStream("sbge.ini");
        if (appConfigStream != null) {
            this.appConfig = new GameConfigFile(appConfigStream);
        } else {
            this.appConfig = new GameConfigFile();
        }

        var defaultConfigStream = GameConfig.class.getResourceAsStream("sbge-defaults.ini");
        if (defaultConfigStream == null) {
            throw new RuntimeException("Internal SPGE error - missing config");
        }
        this.defaultConfig = new GameConfigFile(defaultConfigStream);
    }

    public String get(String section, String option) {
        if (this.appConfig.contains(section, option)) {
            return this.appConfig.get(section, option);
        } else {
            return this.defaultConfig.get(section, option);
        }
    }

    public int getInt(String section, String option) {
        if (this.appConfig.contains(section, option)) {
            return this.appConfig.getInt(section, option);
        } else {
            return this.defaultConfig.getInt(section, option);
        }
    }

    public boolean getBoolean(String section, String option) {
        if (this.appConfig.contains(section, option)) {
            return this.appConfig.getBoolean(section, option);
        } else {
            return this.defaultConfig.getBoolean(section, option);
        }
    }

    public Color getColor(String section, String option) {
        if (this.appConfig.contains(section, option)) {
            return this.appConfig.getColor(section, option);
        } else {
            return this.defaultConfig.getColor(section, option);
        }
    }

    public Iterable<String> getOptions(String section) {
        if (this.appConfig.contains(section)) {
            return this.appConfig.getOptions(section);
        } else {
            return this.defaultConfig.getOptions(section);
        }
    }
}
