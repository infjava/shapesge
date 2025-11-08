package fri.shapesge.engine;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

class GameConfigFile {
    private final HashMap<String, HashMap<String, String>> values;

    GameConfigFile(InputStream config) {
        this.values = new HashMap<>();

        try (var configReader = new Scanner(config)) {
            String section = "default";

            while (configReader.hasNextLine()) {
                var configLine = configReader.nextLine().strip();

                if (configLine.isEmpty() || configLine.startsWith(";")) {
                    continue;
                }

                if (configLine.startsWith("[") && configLine.endsWith("]")) {
                    section = configLine.substring(1, configLine.length() - 1).strip();
                    if (!this.values.containsKey(section)) {
                        this.values.put(section, new HashMap<>());
                    }
                    continue;
                }

                String[] optionAndValue = configLine.split("=", 2);
                if (optionAndValue.length != 2) {
                    throw new RuntimeException("Invalid SPGE config");
                }

                String option = optionAndValue[0].strip();
                String value = optionAndValue[1].strip();

                this.values.get(section).put(option, value);
            }
        }
    }

    public boolean contains(String section, String option) {
        return this.values.containsKey(section) && this.values.get(section).containsKey(option);
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

    public boolean contains(String section) {
        return this.values.containsKey(section);
    }

    public Iterable<GameConfigOption> getOptions(String section) {
        if (this.values.containsKey(section)) {
            return this.values.get(section)
                    .entrySet()
                    .stream()
                    .map(e -> new GameConfigOption(e.getKey(), e.getValue()))
                    ::iterator; // creates iterable out of stream
        } else {
            return Collections.emptyList();
        }
    }
}
