package io.github.rubiksimplosion.minecrafttas.input;

public class InputManager {
    public static InputManager instance;
    public boolean executing = false;

    public InputManager() {
        instance = this;
    }
    public static InputManager getInstance() {
        return instance;
    }

    public void setExecutingStatus(boolean status) {
        executing = status;
    }
}
