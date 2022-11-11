package fri.shapesge;

import java.awt.Color;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

class GameConfig {
    public static final String WINDOW_SECTION = "Window";
    public static final String WINDOW_TITLE = "Title";
    public static final String WINDOW_WIDTH = "Width";
    public static final String WINDOW_HEIGHT = "Height";
    public static final String CANVAS_BACKGROUND = "Background";
    public static final String FPS = "FPS";
    public static final String SHOW_INFO = "ShowInfo";

    public static final String KEYBOARD_SECTION = "Keyboard";

    public static final String MOUSE_SECTION = "Mouse";

    private final HashMap<String, HashMap<String, String>> values;

    public GameConfig() {
        var appConfig = ClassLoader.getSystemResourceAsStream("sbge.ini");
        if (appConfig != null) {
            this.values = this.loadConfigFrom(appConfig);
        } else {
            var defaultConfig = GameConfig.class.getResourceAsStream("sbge-defaults.ini");
            if (defaultConfig != null) {
                this.values = this.loadConfigFrom(defaultConfig);
            } else {
                throw new RuntimeException("Internal SPGE error - missing config");
            }
        }
    }

    private HashMap<String, HashMap<String, String>> loadConfigFrom(InputStream config) {
        try (var configReader = new Scanner(config)) {
            var ret = new HashMap<String, HashMap<String, String>>();
            String section = "default";

            while (configReader.hasNextLine()) {
                var configLine = configReader.nextLine().strip();

                if (configLine.isEmpty() || configLine.startsWith(";")) {
                    continue;
                }

                if (configLine.startsWith("[") && configLine.endsWith("]")) {
                    section = configLine.substring(1, configLine.length() - 1).strip();
                    continue;
                }

                String[] optionAndValue = configLine.split("=", 2);
                if (optionAndValue.length != 2) {
                    throw new RuntimeException("Invalid SPGE config");
                }

                String option = optionAndValue[0].strip();
                String value = optionAndValue[1].strip();

                if (!ret.containsKey(section)) {
                    ret.put(section, new HashMap<>());
                }

                ret.get(section).put(option, value);
            }

            return ret;
        }
    }

    public String get(String section, String option) {
        return this.values.get(section).get(option);
    }

    public int getInt(String section, String option) {
        return Integer.parseInt(this.get(section, option));
    }

    public boolean getBoolean(String section, String option) {
        var value = this.get(section, option).toLowerCase();
        return value.equals("yes") || value.equals("true");
    }

    public Color getColor(String section, String option) {
        return Parser.parseColor(this.get(section, option));
    }

    public Iterable<String> getOptions(String section) {
        if (this.values.containsKey(section)) {
            return this.values.get(section).keySet();
        } else {
            return Collections.emptyList();
        }
    }
}
