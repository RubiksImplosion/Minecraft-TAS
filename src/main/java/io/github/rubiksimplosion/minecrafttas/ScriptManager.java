package io.github.rubiksimplosion.minecrafttas;

import io.github.rubiksimplosion.minecrafttas.input.FakeMouse;
import io.github.rubiksimplosion.minecrafttas.util.MouseUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Pattern;

@Environment(EnvType.CLIENT)
public class ScriptManager {
    public static boolean executing = false;
    private String[] script;
    private int commandIndex = 0;

    public void setScript(String scriptName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:/Script/" + scriptName + ".script"));
            script = reader.lines()
                    .toArray(String[]::new);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        executing = true;
    }

    public void stop() {
        executing = false;
        commandIndex = 0;
    }

    public void executeNextCommand() {
        String[] commands = script[commandIndex].split(";");
        commandIndex++;
        if (commandIndex == script.length) {
            stop();
        }

        for (String command:commands) {
            parseCommand(command.trim());
        }

        System.out.println(Arrays.toString(commands));
    }

    public void parseCommand(String command) {
        if (Pattern.matches("yaw=-?\\d+\\.?\\d*", command)) {
            MouseUtil.changeYaw(Double.parseDouble(command.split("=")[1]));
        }
        else if (Pattern.matches("pitch=-?\\d+\\.?\\d*", command)) {
            MouseUtil.changePitch(Double.parseDouble(command.split("=")[1]));
        }
    }
}
