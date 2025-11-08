package fri.shapesge.engine;

class GameConfigOption {
    private final String option;
    private final String value;

    GameConfigOption(String option, String value) {
        this.option = option;
        this.value = value;
    }

    public String getOption() {
        return this.option;
    }

    public String getValue() {
        return this.value;
    }

    public int getIntValue() {
        return Integer.parseInt(this.value);
    }
}
