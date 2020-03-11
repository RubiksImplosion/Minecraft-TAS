package io.github.rubiksimplosion.minecrafttas.input;

public class InputManager {
    private static InputManager instance;
    public boolean executing = false;

    public InputManager() {
        instance = this;
    }

    public void setExecuting(boolean executing) {
        this.executing = executing;
    }

    public static InputManager getInstance() {
        return instance;
    }
}
