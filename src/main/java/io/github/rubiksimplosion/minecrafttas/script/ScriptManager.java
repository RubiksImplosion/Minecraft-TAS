package io.github.rubiksimplosion.minecrafttas.script;

import io.github.rubiksimplosion.minecrafttas.util.MouseUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

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
    private int waitTimer = 0;

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
        waitTimer = 1;
    }

    public void stop() {
        executing = false;
        commandIndex = 0;
    }

    public void executeNextCommand() {
        if (waitTimer == 0) {
            String[] commands = script[commandIndex].split(";");
            commandIndex++;
            if (commandIndex == script.length) {
                stop();
            }
            if (!commands[0].startsWith("//")) {
                for (String command : commands) {
                    parseCommand(command.trim());
                }
            }

            System.out.println(Arrays.toString(commands));
        } else {
            waitTimer--;
            System.out.println(waitTimer);
        }
    }

    public void parseCommand(String command) {
        if (Pattern.matches("wait \\d+", command)) {
            waitTimer = Integer.parseInt(command.split(" ")[1]);
        }
        else if (Pattern.matches("yaw -?\\d+\\.?\\d*", command)) {
            MouseUtil.changeYaw(Double.parseDouble(command.split(" ")[1]));
        }
        else if (Pattern.matches("pitch -?\\d+\\.?\\d*", command)) {
            MouseUtil.changePitch(Double.parseDouble(command.split(" ")[1]));
        }
        else if (Pattern.matches("\\+attack", command)) {
            MouseUtil.holdAttack();
        }
        else if (Pattern.matches("-attack", command)) {
            MouseUtil.releaseAttack();
        }
        else if (Pattern.matches("\\+use", command)) {
            MouseUtil.holdUse();
        }
        else if (Pattern.matches("-use", command)) {
            MouseUtil.releaseUse();
        }
        else if (Pattern.matches("\\+pickitem", command)) {
            MouseUtil.holdPickItem();
        }
        else if (Pattern.matches("-pickitem", command)) {
            MouseUtil.releasePickItem();
        }
        else if (Pattern.matches("scrollup \\d+", command)) {
            MouseUtil.scrollUp(Double.parseDouble(command.split(" ")[1]));
        }
        else if (Pattern.matches("scrolldown \\d+", command)) {
            MouseUtil.scrollDown(Double.parseDouble(command.split(" ")[1]));
        }
    }
}
