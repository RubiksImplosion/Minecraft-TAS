package io.github.rubiksimplosion.minecrafttas.script;

import io.github.rubiksimplosion.minecrafttas.input.FakeMouse;
import io.github.rubiksimplosion.minecrafttas.util.COMMAND_TYPES;
import io.github.rubiksimplosion.minecrafttas.util.InputUtil;
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
    public static final File scriptDirectory = new File(MinecraftClient.getInstance().runDirectory.getPath() + "\\scripts\\");
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
        commandToCommandType.put("+attack", COMMAND_TYPES.ATTACK_PRESS);
        commandToCommandType.put("-attack", COMMAND_TYPES.ATTACK_RELEASE);
        commandToCommandType.put("+use", COMMAND_TYPES.USE_PRESS);
        commandToCommandType.put("-use", COMMAND_TYPES.USE_RELEASE);
        commandToCommandType.put("+pickitem", COMMAND_TYPES.PICK_ITEM_PRESS);
        commandToCommandType.put("-pickitem", COMMAND_TYPES.PICK_ITEM_RELEASE);
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
        commandToCommandType.put("+chat", COMMAND_TYPES.CHAT_PRESS);
        commandToCommandType.put("-chat", COMMAND_TYPES.CHAT_RELEASE);
        commandToCommandType.put("+command", COMMAND_TYPES.COMMAND_PRESS);
        commandToCommandType.put("-command", COMMAND_TYPES.COMMAND_RELEASE);
        commandToCommandType.put("+escape", COMMAND_TYPES.ESCAPE_PRESS);
        commandToCommandType.put("-escape", COMMAND_TYPES.ESCAPE_RELEASE);
        commandToCommandType.put("+leftmouse", COMMAND_TYPES.LEFT_MOUSE_PRESS);
        commandToCommandType.put("-leftmouse", COMMAND_TYPES.LEFT_MOUSE_RELEASE);
        commandToCommandType.put("+rightmouse", COMMAND_TYPES.RIGHT_MOUSE_PRESS);
        commandToCommandType.put("-rightmouse", COMMAND_TYPES.RIGHT_MOUSE_RELEASE);
        commandToCommandType.put("+middlemouse", COMMAND_TYPES.MIDDLE_MOUSE_PRESS);
        commandToCommandType.put("-middlemouse", COMMAND_TYPES.MIDDLE_MOUSE_RELEASE);
        commandToCommandType.put("+leftshift", COMMAND_TYPES.LEFT_SHIFT_PRESS);
        commandToCommandType.put("-leftshift", COMMAND_TYPES.LEFT_SHIFT_RELEASE);
        commandToCommandType.put("+leftcontrol", COMMAND_TYPES.LEFT_CONTROL_PRESS);
        commandToCommandType.put("-leftcontrol", COMMAND_TYPES.LEFT_CONTROL_RELEASE);
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
        }
    }

    public void stop() {
        executing = false;
        commandIndex = 0;
        waitTimer = 0;
        MinecraftClient.getInstance().player.sendMessage(new LiteralText("Script execution finished"), false);
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
                case AUTO_JUMP_ENABLE: InputUtil.enableAutoJump(); break;
                case AUTO_JUMP_DISABLE: InputUtil.disableAutoJump(); break;
            }
        }
        while (keyInputQueue.size() > 0) {
            switch (keyInputQueue.remove()) {
                case ATTACK_PRESS: InputUtil.pressAttack(); break;
                case ATTACK_RELEASE: InputUtil.releaseAttack(); break;
                case USE_PRESS: InputUtil.pressUse(); break;
                case USE_RELEASE: InputUtil.releaseUse(); break;
                case PICK_ITEM_PRESS: InputUtil.pressPickItem(); break;
                case PICK_ITEM_RELEASE: InputUtil.releasePickItem(); break;
                case JUMP_PRESS: InputUtil.pressJump(); break;
                case JUMP_RELEASE: InputUtil.releaseJump(); break;
                case SNEAK_PRESS: InputUtil.pressSneak(); break;
                case SNEAK_RELEASE: InputUtil.releaseSneak(); break;
                case SPRINT_PRESS: InputUtil.pressSprint(); break;
                case SPRINT_RELEASE: InputUtil.releaseSprint(); break;
                case FORWARD_PRESS: InputUtil.pressForward(); break;
                case FORWARD_RELEASE: InputUtil.releaseForward(); break;
                case LEFT_PRESS: InputUtil.pressLeft(); break;
                case LEFT_RELEASE: InputUtil.releaseLeft(); break;
                case RIGHT_PRESS: InputUtil.pressRight(); break;
                case RIGHT_RELEASE: InputUtil.releaseRight(); break;
                case BACKWARD_PRESS: InputUtil.pressBack(); break;
                case BACKWARD_RELEASE: InputUtil.releaseBack(); break;
                case DROP_PRESS: InputUtil.pressDrop(); break;
                case DROP_RELEASE: InputUtil.releaseDrop(); break;
                case INVENTORY_PRESS: InputUtil.pressInventory(); break;
                case INVENTORY_RELEASE: InputUtil.releaseInventory(); break;
                case SWAP_HAND_PRESS: InputUtil.pressSwapHand(); break;
                case SWAP_HAND_RELEASE: InputUtil.releaseSwapHand(); break;
                case CHAT_PRESS: InputUtil.pressChat(); break;
                case CHAT_RELEASE: InputUtil.releaseChat(); break;
                case COMMAND_PRESS: InputUtil.pressCommand(); break;
                case COMMAND_RELEASE: InputUtil.releaseCommand(); break;
                case ESCAPE_PRESS: InputUtil.pressEscape(); break;
                case ESCAPE_RELEASE: InputUtil.releaseEscape(); break;
                case LEFT_MOUSE_PRESS: InputUtil.pressMouseButton(0); break;
                case LEFT_MOUSE_RELEASE: InputUtil.releaseMouseButton(0); break;
                case RIGHT_MOUSE_PRESS: InputUtil.pressMouseButton(1); break;
                case RIGHT_MOUSE_RELEASE: InputUtil.releaseMouseButton(1); break;
                case MIDDLE_MOUSE_PRESS: InputUtil.pressMouseButton(2); break;
                case MIDDLE_MOUSE_RELEASE: InputUtil.releaseMouseButton(2); break;
                case LEFT_SHIFT_PRESS: InputUtil.pressLeftShift(); break;
                case LEFT_SHIFT_RELEASE: InputUtil.releaseLeftShift(); break;
                case LEFT_CONTROL_PRESS: InputUtil.pressLeftControl(); break;
                case LEFT_CONTROL_RELEASE: InputUtil.releaseLeftControl(); break;
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
            InputUtil.changeYaw(Double.parseDouble(command.split(" ")[1]));
        }
        else if (Pattern.matches("pitch -?\\d+\\.?\\d*", command)) {
            InputUtil.changePitch(Double.parseDouble(command.split(" ")[1]));
        }
        else if (Pattern.matches("scrollup \\d+", command)) {
            for (int i = 0; i < Integer.parseInt(command.split(" ")[1]); i++) {
                FakeMouse.fakeMouseScroll(1.0);
            }
        }
        else if (Pattern.matches("scrolldown \\d+", command)) {
            for (int i = 0; i < Integer.parseInt(command.split(" ")[1]); i++) {
                FakeMouse.fakeMouseScroll(-1.0);
            }
        }
        else if (Pattern.matches("\\+hotbar\\d", command)) {
            int slot = Integer.parseInt(command.substring(7, 8));
            if (slot != 0) {
                InputUtil.pressHotbar(slot);
            }
        }
        else if (Pattern.matches("-hotbar\\d", command)) {
            int slot = Integer.parseInt(command.substring(7, 8));
            if (slot != 0) {
                InputUtil.releaseHotbar(slot);
            }
        }
        else if (Pattern.matches("slot \\d+", command)) {
              InputUtil.moveMouseToSlot(Integer.parseInt(command.split(" ")[1]));
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

/*
//done
key.hotbar.1
key.hotbar.2
key.hotbar.3
key.hotbar.4
key.hotbar.5
key.hotbar.6
key.hotbar.7
key.hotbar.8
key.hotbar.9
key.attack
key.use
key.pickItem
key.forward
key.back
key.left
key.right
key.sneak
key.sprint
key.jump
key.inventory
key.drop
key.swapOffhand
key.chat
key.command

scroll
escape

F3 (debug menu)
F4 (shader)*
narrator*
debug crash*
key.playerlist
key.saveToolbarActivator*
key.loadToolbarActivator*
key.screenshot
key.togglePerspective
key.smoothCamera
key.fullscreen*

key.advancements
key.spectatorOutlines*

 *very low priority
 */
