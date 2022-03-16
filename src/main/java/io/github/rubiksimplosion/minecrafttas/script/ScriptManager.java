package io.github.rubiksimplosion.minecrafttas.script;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.input.FakeMouse;
import io.github.rubiksimplosion.minecrafttas.util.COMMAND_TYPES;
import io.github.rubiksimplosion.minecrafttas.util.InputUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

@Environment(EnvType.CLIENT)
public class ScriptManager {
    public static final File scriptDirectory = initializeScriptDirectory();
    public boolean executing = false;
    public boolean fakeInput = false;

    /**
     * Controls the keyboard and mouse input modifiers (shift, control, alt; other input modifiers are not relevant to minecraft)
     */
    public int modifiers = 0;
    private String[] script;
    private int commandIndex = 0;
    private int waitTimer = 0;
    private int length = 0;
    private final Queue<COMMAND_TYPES> commandInputQueue = new LinkedList<>();  // standard commands
    private final Queue<COMMAND_TYPES> specialInputQueue = new LinkedList<>();  // always executed first

    private final Queue<Integer> integerQueue = new LinkedList<>(); // used for mouse scrolling
    private final Queue<String> stringQueue = new LinkedList<>();  // used for simulating typing
    private final Queue<ItemGroup> itemGroupQueue = new LinkedList<>(); // used for creative tabs
    private final Queue<Double> doubleQueue = new LinkedList<>();   // used for mouse scrolling and yaw/pitch
    private final Map<String, COMMAND_TYPES> commandToCommandType = new HashMap<>();
    private final Map<String, COMMAND_TYPES> specialToCommandType = new HashMap<>();
    private final Map<String, ItemGroup> stringToItemGroup = new HashMap<>();

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
        commandToCommandType.put("+hotbar1", COMMAND_TYPES.HOTBAR_1_PRESS);
        commandToCommandType.put("+hotbar2", COMMAND_TYPES.HOTBAR_2_PRESS);
        commandToCommandType.put("+hotbar3", COMMAND_TYPES.HOTBAR_3_PRESS);
        commandToCommandType.put("+hotbar4", COMMAND_TYPES.HOTBAR_4_PRESS);
        commandToCommandType.put("+hotbar5", COMMAND_TYPES.HOTBAR_5_PRESS);
        commandToCommandType.put("+hotbar6", COMMAND_TYPES.HOTBAR_6_PRESS);
        commandToCommandType.put("+hotbar7", COMMAND_TYPES.HOTBAR_7_PRESS);
        commandToCommandType.put("+hotbar8", COMMAND_TYPES.HOTBAR_8_PRESS);
        commandToCommandType.put("+hotbar9", COMMAND_TYPES.HOTBAR_9_PRESS);
        commandToCommandType.put("-hotbar1", COMMAND_TYPES.HOTBAR_1_RELEASE);
        commandToCommandType.put("-hotbar2", COMMAND_TYPES.HOTBAR_2_RELEASE);
        commandToCommandType.put("-hotbar3", COMMAND_TYPES.HOTBAR_3_RELEASE);
        commandToCommandType.put("-hotbar4", COMMAND_TYPES.HOTBAR_4_RELEASE);
        commandToCommandType.put("-hotbar5", COMMAND_TYPES.HOTBAR_5_RELEASE);
        commandToCommandType.put("-hotbar6", COMMAND_TYPES.HOTBAR_6_RELEASE);
        commandToCommandType.put("-hotbar7", COMMAND_TYPES.HOTBAR_7_RELEASE);
        commandToCommandType.put("-hotbar8", COMMAND_TYPES.HOTBAR_8_RELEASE);
        commandToCommandType.put("-hotbar9", COMMAND_TYPES.HOTBAR_9_RELEASE);
        commandToCommandType.put("+enter", COMMAND_TYPES.ENTER_PRESS);
        commandToCommandType.put("-enter", COMMAND_TYPES.ENTER_RELEASE);
        //special
        specialToCommandType.put("+autojump", COMMAND_TYPES.AUTO_JUMP_ENABLE);
        specialToCommandType.put("-autojump", COMMAND_TYPES.AUTO_JUMP_DISABLE);
        //creative tabs
        stringToItemGroup.put("building", ItemGroup.BUILDING_BLOCKS);
        stringToItemGroup.put("decoration", ItemGroup.DECORATIONS);
        stringToItemGroup.put("redstone", ItemGroup.REDSTONE);
        stringToItemGroup.put("transportation", ItemGroup.TRANSPORTATION);
        stringToItemGroup.put("hotbar", ItemGroup.HOTBAR);
        stringToItemGroup.put("search", ItemGroup.SEARCH);
        stringToItemGroup.put("misc", ItemGroup.MISC);
        stringToItemGroup.put("food", ItemGroup.FOOD);
        stringToItemGroup.put("tools", ItemGroup.TOOLS);
        stringToItemGroup.put("combat", ItemGroup.COMBAT);
        stringToItemGroup.put("brewing", ItemGroup.BREWING);
        stringToItemGroup.put("inventory", ItemGroup.INVENTORY);
    }

    public boolean isScriptLoaded() {
        return this.script != null && this.script.length > 0;
    }

    public int setScript(String scriptName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scriptDirectory + System.getProperty("file.separator") + scriptName + ".script"));
            script = reader.lines()
                    .toArray(String[]::new);
            length += script.length;
            for (int i = 0; i < script.length; i++) {
                String[] commands = Arrays.stream(script[i].split(";"))
                        .map(String::trim)
                        .toArray(String[]::new);
                if (!commands[0].startsWith("//")) {
//                    for (int j = 0; j < commands.length; j++) {
                    for (String command : commands) {
//                        String command = script[j].trim();
                        command = command.trim();
                        if (Pattern.matches("wait \\d+", command)) {
                            length += Integer.parseInt(command.split(" ")[1]);
                        }
                        if (Pattern.matches("tab .+", command)) {
                            if (stringToItemGroup.get(command.split(" ")[1]) == null) {
                                InputUtil.sendError(new TranslatableText("error.command.load.invalidTab", scriptName, command, i + 1));
                                for (String key : stringToItemGroup.keySet()) {
                                    InputUtil.sendError(new LiteralText("  " + key));
                                }
                                script = null;
                                length = 0;
                                return 3; // invalid command
                            }
                        }
                        if(!(Pattern.matches("wait \\d+", command) ||
                                commandToCommandType.containsKey(command) ||
                                specialToCommandType.containsKey(command) ||
                                Pattern.matches("yaw -?\\d+\\.?\\d*", command) ||
                                Pattern.matches("pitch -?\\d+\\.?\\d*", command) ||
                                Pattern.matches("scrollup \\d+", command) ||
                                Pattern.matches("scrolldown \\d+", command) ||
//                                Pattern.matches("\\+hotbar\\d", command) ||
//                                Pattern.matches("-hotbar\\d", command) ||
                                Pattern.matches("slot \\d+", command) ||
                                Pattern.matches("load.*", command) ||
                                Pattern.matches("text \".*\"", command) ||
                                Pattern.matches("tab .+", command)))
                        {
                            InputUtil.sendError(new TranslatableText("error.command.load.invalid", scriptName, command, i + 1));
                            script = null;
                            length = 0;
                            return 3; // invalid command
                        }
                    }
                }
            }
            if (script.length > 0) {
                return 0; // success
            }
            script = null;
            length = 0;
            return 1; // script is empty
        } catch (FileNotFoundException e) {
            return 2; // script does not exist
        }
    }

    public void start() {
        if (script != null) {
//            prevTick = ((MinecraftServerAccessor)MinecraftClient.getInstance().getServer()).getTicks();

//            ((RenderTickCounterAccessor)((MinecraftClientAccessor)MinecraftClient.getInstance()).getRenderTickCounter()).setTickTime(25);
            executing = true;
            waitTimer = 0;
        }
    }

    public void stop() {
        executing = false;
        commandIndex = 0;
        waitTimer = 0;
        modifiers = 0;
        length = 0;
        commandInputQueue.clear();
        specialInputQueue.clear();
        stringQueue.clear();
        itemGroupQueue.clear();
        InputUtil.sendFeedback(new TranslatableText("execution.finish"));
    }

    public void setupTick() {
            if (waitTimer == 0 /*&& !((ServerPlayerEntityAccessor)InputUtil.getServerSidePlayerEntity()).isInTeleportationState()*/) {
                if (commandIndex == script.length) {
                    stop();
                    return;
                }
                String[] commands = Arrays.stream(script[commandIndex].split(";"))
                        .map(String::trim)
                        .toArray(String[]::new);
                if (!commands[0].startsWith("//")) {
                    for (String command : commands) {
                        parseCommand(command.trim());
                    }
                }
                commandIndex++;
                System.out.println(Arrays.toString(commands));
            } else {
                waitTimer--;
                System.out.println(waitTimer);
            }
//        }
    }

    public void executeTick() {
        while (specialInputQueue.size() > 0) {
            switch (specialInputQueue.remove()) {
                case AUTO_JUMP_ENABLE -> InputUtil.enableAutoJump();
                case AUTO_JUMP_DISABLE -> InputUtil.disableAutoJump();
            }
        }
        while (commandInputQueue.size() > 0) {
            switch (commandInputQueue.remove()) {
                case ATTACK_PRESS -> InputUtil.pressAttack();
                case ATTACK_RELEASE -> InputUtil.releaseAttack();
                case USE_PRESS -> InputUtil.pressUse();
                case USE_RELEASE -> InputUtil.releaseUse();
                case PICK_ITEM_PRESS -> InputUtil.pressPickItem();
                case PICK_ITEM_RELEASE -> InputUtil.releasePickItem();
                case JUMP_PRESS -> InputUtil.pressJump();
                case JUMP_RELEASE -> InputUtil.releaseJump();
                case SNEAK_PRESS -> InputUtil.pressSneak();
                case SNEAK_RELEASE -> InputUtil.releaseSneak();
                case SPRINT_PRESS -> InputUtil.pressSprint();
                case SPRINT_RELEASE -> InputUtil.releaseSprint();
                case FORWARD_PRESS -> InputUtil.pressForward();
                case FORWARD_RELEASE -> InputUtil.releaseForward();
                case LEFT_PRESS -> InputUtil.pressLeft();
                case LEFT_RELEASE -> InputUtil.releaseLeft();
                case RIGHT_PRESS -> InputUtil.pressRight();
                case RIGHT_RELEASE -> InputUtil.releaseRight();
                case BACKWARD_PRESS -> InputUtil.pressBack();
                case BACKWARD_RELEASE -> InputUtil.releaseBack();
                case DROP_PRESS -> InputUtil.pressDrop();
                case DROP_RELEASE -> InputUtil.releaseDrop();
                case INVENTORY_PRESS -> InputUtil.pressInventory();
                case INVENTORY_RELEASE -> InputUtil.releaseInventory();
                case SWAP_HAND_PRESS -> InputUtil.pressSwapHand();
                case SWAP_HAND_RELEASE -> InputUtil.releaseSwapHand();
                case CHAT_PRESS -> InputUtil.pressChat();
                case CHAT_RELEASE -> InputUtil.releaseChat();
                case COMMAND_PRESS -> InputUtil.pressCommand();
                case COMMAND_RELEASE -> InputUtil.releaseCommand();
                case ESCAPE_PRESS -> InputUtil.pressEscape();
                case ESCAPE_RELEASE -> InputUtil.releaseEscape();
                case LEFT_MOUSE_PRESS -> InputUtil.pressMouseButton(0);
                case LEFT_MOUSE_RELEASE -> InputUtil.releaseMouseButton(0);
                case RIGHT_MOUSE_PRESS -> InputUtil.pressMouseButton(1);
                case RIGHT_MOUSE_RELEASE -> InputUtil.releaseMouseButton(1);
                case MIDDLE_MOUSE_PRESS -> InputUtil.pressMouseButton(2);
                case MIDDLE_MOUSE_RELEASE -> InputUtil.releaseMouseButton(2);
                case LEFT_SHIFT_PRESS -> InputUtil.pressLeftShift();
                case LEFT_SHIFT_RELEASE -> InputUtil.releaseLeftShift();
                case LEFT_CONTROL_PRESS -> InputUtil.pressLeftControl();
                case LEFT_CONTROL_RELEASE -> InputUtil.releaseLeftControl();
                case HOTBAR_1_PRESS -> InputUtil.pressHotbar(1);
                case HOTBAR_2_PRESS -> InputUtil.pressHotbar(2);
                case HOTBAR_3_PRESS -> InputUtil.pressHotbar(3);
                case HOTBAR_4_PRESS -> InputUtil.pressHotbar(4);
                case HOTBAR_5_PRESS -> InputUtil.pressHotbar(5);
                case HOTBAR_6_PRESS -> InputUtil.pressHotbar(6);
                case HOTBAR_7_PRESS -> InputUtil.pressHotbar(7);
                case HOTBAR_8_PRESS -> InputUtil.pressHotbar(8);
                case HOTBAR_9_PRESS -> InputUtil.pressHotbar(9);
                case HOTBAR_1_RELEASE -> InputUtil.releaseHotbar(1);
                case HOTBAR_2_RELEASE -> InputUtil.releaseHotbar(2);
                case HOTBAR_3_RELEASE -> InputUtil.releaseHotbar(3);
                case HOTBAR_4_RELEASE -> InputUtil.releaseHotbar(4);
                case HOTBAR_5_RELEASE -> InputUtil.releaseHotbar(5);
                case HOTBAR_6_RELEASE -> InputUtil.releaseHotbar(6);
                case HOTBAR_7_RELEASE -> InputUtil.releaseHotbar(7);
                case HOTBAR_8_RELEASE -> InputUtil.releaseHotbar(8);
                case HOTBAR_9_RELEASE -> InputUtil.releaseHotbar(9);
                case ENTER_PRESS -> InputUtil.pressEnter();
                case ENTER_RELEASE -> InputUtil.releaseEnter();
                case TAB -> InputUtil.moveMouseToTab(itemGroupQueue.remove());
                case YAW -> InputUtil.changeYaw(doubleQueue.remove());
                case PITCH -> InputUtil.changePitch(doubleQueue.remove());
                case SLOT -> InputUtil.moveMouseToSlot(integerQueue.remove());
                case SCROLL_UP -> {
                    int scrolls = integerQueue.remove();
                    for (int i = 0; i < scrolls; i++) {
                        FakeMouse.fakeMouseScroll(1.0);
                    }
                }
                case SCROLL_DOWN -> {
                    int scrolls = integerQueue.remove();
                    for (int i = 0; i < scrolls; i++) {
                        FakeMouse.fakeMouseScroll(-1.0);
                    }
                }
                case TEXT -> {
                    String text = stringQueue.remove();
                    for (char c : text.toCharArray()) {
                        InputUtil.typeLiteralChar(c);
                    }
                }
                default -> {
                    InputUtil.sendError(new TranslatableText("error.execute.unknown", commandIndex));
                    stop();
                }
            }
        }
    }

    // adds the current instruction to the correct queue
    private void parseCommand(String command) {
        if (Pattern.matches("wait \\d+", command)) {
            waitTimer = Integer.parseInt(command.split(" ")[1]);
        }
        if (commandToCommandType.containsKey(command)) {
            commandInputQueue.add(commandToCommandType.get(command));
        }
        else if (specialToCommandType.containsKey(command)) {
            specialInputQueue.add(specialToCommandType.get(command));
        }
        else if (Pattern.matches("yaw -?\\d+\\.?\\d*", command)) {
            doubleQueue.add(Double.parseDouble(command.split(" ")[1]));
            commandInputQueue.add(COMMAND_TYPES.YAW);
        }
        else if (Pattern.matches("pitch -?\\d+\\.?\\d*", command)) {
            doubleQueue.add(Double.parseDouble(command.split(" ")[1]));
            commandInputQueue.add(COMMAND_TYPES.PITCH);
        }
        else if (Pattern.matches("scrollup \\d+", command)) {
            integerQueue.add(Integer.parseInt(command.split(" ")[1]));
            commandInputQueue.add(COMMAND_TYPES.SCROLL_UP);
        }
        else if (Pattern.matches("scrolldown \\d+", command)) {
            integerQueue.add(Integer.parseInt(command.split(" ")[1]));
            commandInputQueue.add(COMMAND_TYPES.SCROLL_DOWN);
        }
        else if (Pattern.matches("slot \\d+", command)) {
            integerQueue.add(Integer.parseInt(command.split(" ")[1]));
            commandInputQueue.add(COMMAND_TYPES.SLOT);
        }
        else if (Pattern.matches("load.*", command)) {
            if (command.split(" ").length > 1 && command.split(" ")[1].length() > 0) {
                MinecraftTas.savestateManager.loadNamedSoftSavestate(command.split(" ")[1]);
            } else {
                MinecraftTas.savestateManager.loadMostRecentSavestate();
            }
        }
        else if (Pattern.matches("text \".*\"", command)) {
            stringQueue.add(command.substring(6, command.length() - 1));
            commandInputQueue.add(COMMAND_TYPES.TEXT);
        }
        else if (Pattern.matches("tab .+", command)) {
            itemGroupQueue.add(stringToItemGroup.get(command.split(" ")[1]));
            commandInputQueue.add(COMMAND_TYPES.TAB);
        }
    }

    private static File initializeScriptDirectory() {
        File scriptDirectory = new File(MinecraftClient.getInstance().runDirectory.getPath() + System.getProperty("file.separator") + "scripts" + System.getProperty("file.separator"));
        if (!scriptDirectory.exists()) {
            scriptDirectory.mkdir();
        }
        return scriptDirectory;
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
