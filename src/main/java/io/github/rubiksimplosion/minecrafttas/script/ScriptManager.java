package io.github.rubiksimplosion.minecrafttas.script;

import io.github.rubiksimplosion.minecrafttas.util.KeyboardUtil;
import io.github.rubiksimplosion.minecrafttas.util.MouseUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Pattern;

@Environment(EnvType.CLIENT)
public class ScriptManager {
    public static final File scriptDirectory = new File((MinecraftClient.getInstance().runDirectory.getPath() + "scripts\\").replace(".", ""));
    public boolean executing = false;
    public boolean fakeInput = false;
    /**
     * Controls the keyboard and mouse input modifiers (shift, control, alt; other input modifiers are not relevant to minecraft)
     */
    public int modifiers = 0;
    private String[] script;
    private int commandIndex = 0;
    private int waitTimer = 0;

    public void setScript(String scriptName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scriptDirectory + "\\" + scriptName + ".script"));
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
        waitTimer = 0;
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
        } else {
            MinecraftClient.getInstance().player.sendMessage(new LiteralText(command), false);
        }
        //Mouse
        if (Pattern.matches("yaw -?\\d+\\.?\\d*", command)) {
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

        //Keyboard
        else if (Pattern.matches("\\+jump", command)) {
            KeyboardUtil.holdJump();
        }
        else if (Pattern.matches("-jump", command)) {
            KeyboardUtil.releaseJump();
        }
        else if (Pattern.matches("\\+sneak", command)) {
            KeyboardUtil.holdSneak();
        }
        else if (Pattern.matches("-sneak", command)) {
            KeyboardUtil.releaseSneak();
        }
        else if (Pattern.matches("\\+sprint", command)) {
            KeyboardUtil.holdSprint();
        }
        else if (Pattern.matches("-sprint", command)) {
            KeyboardUtil.releaseSprint();
        }
        else if (Pattern.matches("\\+forward", command)) {
            KeyboardUtil.holdForward();
        }
        else if (Pattern.matches("-forward", command)) {
            KeyboardUtil.releaseForward();
        }
        else if (Pattern.matches("\\+back", command)) {
            KeyboardUtil.holdBack();
        }
        else if (Pattern.matches("-back", command)) {
            KeyboardUtil.releaseBack();
        }
        else if (Pattern.matches("\\+left", command)) {
            KeyboardUtil.holdLeft();
        }
        else if (Pattern.matches("-left", command)) {
            KeyboardUtil.releaseLeft();
        }
        else if (Pattern.matches("\\+right", command)) {
            KeyboardUtil.holdRight();
        }
        else if (Pattern.matches("-right", command)) {
            KeyboardUtil.releaseRight();
        }
        else if (Pattern.matches("\\+autojump", command)) {
            //TO IMPLEMENT
        }
        else if (Pattern.matches("-autojump", command)) {
            //TO IMPLEMENT
        }
    }
}
