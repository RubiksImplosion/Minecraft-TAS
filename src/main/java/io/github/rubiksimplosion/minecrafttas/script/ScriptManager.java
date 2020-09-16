package io.github.rubiksimplosion.minecrafttas.script;

import io.github.rubiksimplosion.minecrafttas.util.COMMAND_TYPES;
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
import java.util.*;
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


    private final Queue<COMMAND_TYPES> keyInputQueue = new LinkedList<COMMAND_TYPES>();
    private final Queue<COMMAND_TYPES> specialInputQueue = new LinkedList<COMMAND_TYPES>();

    private final Map<String, COMMAND_TYPES> commandToCommandType = new HashMap<>();
    private final Map<String, COMMAND_TYPES> specialToCommandType = new HashMap<>();
    public ScriptManager() {
        //mouse
        commandToCommandType.put("+attack", COMMAND_TYPES.ATTACK_PRESS);
        commandToCommandType.put("-attack", COMMAND_TYPES.ATTACK_RELEASE);
        commandToCommandType.put("+use", COMMAND_TYPES.USE_PRESS);
        commandToCommandType.put("-use", COMMAND_TYPES.USE_RELEASE);
        commandToCommandType.put("+pickitem", COMMAND_TYPES.PICK_ITEM_PRESS);
        commandToCommandType.put("-pickitem", COMMAND_TYPES.PICK_ITEM_RELEASE);
        //keyboard
        commandToCommandType.put("+jump", COMMAND_TYPES.JUMP_PRESS);
        commandToCommandType.put("-jump", COMMAND_TYPES.JUMP_RELEASE);
        commandToCommandType.put("+sneak", COMMAND_TYPES.SNEAK_PRESS);
        commandToCommandType.put("-sneak", COMMAND_TYPES.SNEAK_RELEASE);
        commandToCommandType.put("+sprint", COMMAND_TYPES.SPRINT_PRESS);
        commandToCommandType.put("-sprint", COMMAND_TYPES.SPRINT_RELEASE);
        commandToCommandType.put("+forward", COMMAND_TYPES.FORWARD_PRESS);
        commandToCommandType.put("-forward", COMMAND_TYPES.FORWARD_RELEASE);
        commandToCommandType.put("+left", COMMAND_TYPES.LEFT_PRESS);
        commandToCommandType.put("-left",  COMMAND_TYPES.LEFT_RELEASE);
        commandToCommandType.put("+right", COMMAND_TYPES.RIGHT_PRESS);
        commandToCommandType.put("-right", COMMAND_TYPES.RIGHT_RELEASE);
        commandToCommandType.put("+back", COMMAND_TYPES.BACKWARD_PRESS);
        commandToCommandType.put("-back", COMMAND_TYPES.BACKWARD_RELEASE);
        commandToCommandType.put("+drop", COMMAND_TYPES.DROP_PRESS);
        commandToCommandType.put("-drop", COMMAND_TYPES.DROP_RELEASE);
        commandToCommandType.put("+inventory", COMMAND_TYPES.INVENTORY_PRESS);
        commandToCommandType.put("-inventory", COMMAND_TYPES.INVENTORY_RELEASE);
        commandToCommandType.put("+swaphand", COMMAND_TYPES.SWAP_HAND_PRESS);
        commandToCommandType.put("-swaphand", COMMAND_TYPES.SWAP_HAND_RELEASE);
        //special
        specialToCommandType.put("+autojump", COMMAND_TYPES.AUTO_JUMP_ENABLE);
        specialToCommandType.put("-autojump", COMMAND_TYPES.AUTO_JUMP_DISABLE);
    }

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
        if (script != null) {
            executing = true;
            waitTimer = 1;
            MinecraftClient.getInstance().player.sendMessage(new LiteralText("started"), false);
        }
    }

    public void stop() {
        executing = false;
        commandIndex = 0;
        waitTimer = 0;
        MinecraftClient.getInstance().player.sendMessage(new LiteralText("stopped"), false);
    }

    public void setupTick() {
        if (waitTimer == 0) {
            String[] commands = Arrays.stream(script[commandIndex].split(";"))
                    .map(String::trim)
                    .toArray(String[]::new);
            commandIndex++;
            if (!commands[0].startsWith("//")) {
                for (String command : commands) {
                    parseCommand(command.trim());
                }
            }
            if (commandIndex == script.length) {
                stop();
            }
            System.out.println(Arrays.toString(commands));
        } else {
            waitTimer--;
            System.out.println(waitTimer);
        }
    }

    public void executeTick() {
        while (specialInputQueue.size() > 0) {
            switch (specialInputQueue.remove()) {
                case AUTO_JUMP_ENABLE: KeyboardUtil.enableAutoJump(); break;
                case AUTO_JUMP_DISABLE: KeyboardUtil.disableAutoJump(); break;
            }
        }
        while (keyInputQueue.size() > 0) {
            switch (keyInputQueue.remove()) {
                case ATTACK_PRESS: MouseUtil.pressAttack(); break;
                case ATTACK_RELEASE: MouseUtil.releaseAttack(); break;
                case USE_PRESS: MouseUtil.pressUse(); break;
                case USE_RELEASE: MouseUtil.releaseUse(); break;
                case PICK_ITEM_PRESS: MouseUtil.pressPickItem(); break;
                case PICK_ITEM_RELEASE: MouseUtil.releasePickItem(); break;
                case JUMP_PRESS: KeyboardUtil.pressJump(); break;
                case JUMP_RELEASE: KeyboardUtil.releaseJump(); break;
                case SNEAK_PRESS: KeyboardUtil.pressSneak(); break;
                case SNEAK_RELEASE: KeyboardUtil.releaseSneak(); break;
                case SPRINT_PRESS: KeyboardUtil.pressSprint(); break;
                case SPRINT_RELEASE: KeyboardUtil.releaseSprint(); break;
                case FORWARD_PRESS: KeyboardUtil.pressForward(); break;
                case FORWARD_RELEASE: KeyboardUtil.releaseForward(); break;
                case LEFT_PRESS: KeyboardUtil.pressLeft(); break;
                case LEFT_RELEASE: KeyboardUtil.releaseLeft(); break;
                case RIGHT_PRESS: KeyboardUtil.pressRight(); break;
                case RIGHT_RELEASE: KeyboardUtil.releaseRight(); break;
                case BACKWARD_PRESS: KeyboardUtil.pressBack(); break;
                case BACKWARD_RELEASE: KeyboardUtil.releaseBack(); break;
                case DROP_PRESS: KeyboardUtil.pressDrop(); break;
                case DROP_RELEASE: KeyboardUtil.releaseDrop(); break;
                case INVENTORY_PRESS: KeyboardUtil.pressInventory(); break;
                case INVENTORY_RELEASE: KeyboardUtil.releaseInventory(); break;
                case SWAP_HAND_PRESS: KeyboardUtil.pressSwapHand(); break;
                case SWAP_HAND_RELEASE: KeyboardUtil.releaseSwapHand(); break;
                default:
                    throw new RuntimeException("Unknown input in input queue");
            }
        }
    }

    public void parseCommand(String command) {
        if (Pattern.matches("wait \\d+", command)) {
            waitTimer = Integer.parseInt(command.split(" ")[1]);
        }
        //adds the current instruction to the correct queue
        if (commandToCommandType.containsKey(command)) {
            keyInputQueue.add(commandToCommandType.get(command));
        }
        else if (specialToCommandType.containsKey(command)) {
            specialInputQueue.add(specialToCommandType.get(command));
        }
        //Mouse
        else if (Pattern.matches("yaw -?\\d+\\.?\\d*", command)) {
            MouseUtil.changeYaw(Double.parseDouble(command.split(" ")[1]));
        }
        else if (Pattern.matches("pitch -?\\d+\\.?\\d*", command)) {
            MouseUtil.changePitch(Double.parseDouble(command.split(" ")[1]));
        }
        else if (Pattern.matches("scrollup \\d+", command)) {
            MouseUtil.scrollUp(Double.parseDouble(command.split(" ")[1]));
        }
        else if (Pattern.matches("scrolldown \\d+", command)) {
            MouseUtil.scrollDown(Double.parseDouble(command.split(" ")[1]));
        }
    }
}

/*
ORDER OF INPUT LOGIC:
Perspective
Smooth Camera
Hotbar/Toolbars
Inventory
Advancement
Swap Hands
Drop Item
Chat/Command
Sprint

If (Using item  "ex: place block")
    clear all mouse input buffers
Else
    Use
    Attack
    PickBlock

Use key held down logic ("ex: hold use on door")
Block breaking
*/