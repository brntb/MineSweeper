package minesweeper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum State {

    MINE("X"),
    OPEN("."),
    MARKER("*"),
    FREE("/"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8");

    private final String state;
    private static final Map<String, State> ENUM_MAP;

    State(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    static {
        Map<String, State> map = new HashMap<>();
        for (State current : State.values()) {
            map.put(current.getState(), current);
        }

        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static State get(String name) {
        return ENUM_MAP.get(name);
    }

}
