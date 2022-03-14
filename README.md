# Minecraft-TAS
The goal of this mod is to allow for "emulator-style" TASes of Minecraft to be created; I.E having script files be created and replayed back as if the keys were actually being pressed on a real keyboard.

The word "real" is important to this mod, as every command has been implemented by directly emulating mouse/keyboard inputs. This is to ensure that anything a TAS does would theoretically be possible to do on a real mouse/keyboard given inhuman speed and precision.

This mod still has a lot of work to be done until I can truly be happy with it, but for the time being feel free to use what it has and more stuff will be added in the future.

Some features I would like to eventually implement (in no particular order):
* Full save-states for the player ("soft" save-states)
* Full save-states for the world ("hard" save-states)
* Making the world deterministic (The same set of inputs will lead to the same results; Currently not the case due to RNG)
* Utilities for script creation such as GUIs, freezing, tick-by-tick execution, macros, rewind, user input recording, etc...
* Tools for manipulating RNG*

*These tools would help with the manipulation of RNG, but not outright sidestep it. For example, a RNG tool for getting flint from gravel would NOT give you flint from gravel every time you broke a gravel, but rather tell you on which tick and under what conditions a flint would drop from a gravel if you were to break one.

## Installation
1. Download and run the [Fabric installer](https://fabricmc.net/use).
   - Click the "vanilla" button, leave the other settings as they are,
     and click "download installer".
   - Note: this step may vary if you aren't using the vanilla launcher
     or an old version of Minecraft.
1. Download the [Fabric API](https://minecraft.curseforge.com/projects/fabric)
   and move it to the mods folder (`.minecraft/mods`).
1. Download MinecraftTAS from the [releases page](https://github.com/RubiksImplosion/Minecraft-TAS/releases)
   and move it to the mods folder (`.minecraft/mods`).
   
## Usage
1. Create a text document in the `.minecraft/scripts` folder and rename the extension to ".script"
2. Add the commands in chronological order. Each new line is executed 1 tick after the previous line. Example scipts can be found in the scripts folder
3. Load scripts with the `/script load <script name>` command, it will autocomplete the names of valid script files
4. Start scripts with the `/script start` command, or by pressing the start script keybind (default `O`)
5. Stop scripts with the `/script stop` command, or by pressing the stop script keybind (default `P`)
6. Create a save-state by pressing the create save-state keybind (default `J`)
7. Load a save-state by pressing the load save-state keybind (default `K`)
8. Delete a save-state by pressing the delete save-state keybind (default `'`/`APOSTROPHE`)

For example scripts, see the `run/scripts` folder above
### Available commands:
 
 `wait <ticks>` pauses execution of the script for the given number of ticks 
 
`+autojump|-autojump` - Enables automatic jumping the moment the player hits the ground

`yaw <value>` - Sets the player's yaw to the specified value*
 
`pitch <value>` - Sets the player's pitch to the specified value*

`scrollup <value>` - Scrolls the mouse wheel up the given amount

`scrolldown <value>` - Scrolls the mouse wheel down the given amount

`slot <id>` - Moves the mouse over the given slot in an inventory.

`load` - Loads the most recent save-state

`text "<value>"` - Types the given text into the current screen. Quotes around the text are mandatory.

`+attack|-attack` - Presses and releases the Attack/Destroy key respectively

`+use|-use` - Presses and releases the Use/Place key respectively

`+pickitem|-pickitem` - Presses and releases the pick item key respectively

`+forward|-forward` - Pressed and releases the forward strafe key respectively

`+back|-back` - Presses and releases the backwards strafe key respectively

`+left|-left` - Presses and releases the left strafe key respectively

`+right|-right` - Presses and releases the right strafe key respectively

`+sneak|-sneak` - Presses and releases the sneak key respectively

`+sprint|-sprint` - Presses and releases the sprint key respectively

`+jump|-jump` - Presses and releases the jump key respectively

`+drop|-drop` - Presses and releases the drop item key respectively

`+inventory|-inventory` - Presses and releases the open inventory key respectively

`+swaphand|-swaphand` - Presses and releases the key to swap hands respectively

`+hotbar<1-9>|-hotbar<1-9>` - Presses and releases the specified hotbar key respectively

`+chat|-chat` - Presses and releases the chat key respectively

`+command|-command` - Presses and releases the command key respectively

`+escape|-escape` - Presses and releases the escape key respectively

`+leftmouse|-leftmouse` - Presses and releases the left mouse button respectively

`+rightmouse|-rightmouse` - Presses and releases the right mouse button respectively

`+middlemouse|-middlemouse` - Presses and releases the middle mouse button respectively

`+leftshift|-leftshift` - Presses and releases left shift respectively

`+leftcontrol|-leftcontrol` - Presses and releases left control respectively

`+enter|-enter` - Presses and releases enter respectively

Insert a semicolon `;` between commands on the same line to execute them both on the same tick
For example, the command `+forward; +sprint` would begin pressing forward and sprint on the same tick.

For a list of slot ids, visit https://wiki.vg/Inventory

*Due to mouse inputs being simulated directly, there can be slight inconsistencies
with player camera movement due to the math related to mouse sensitivity. For the most accurate player camera movement
set your in-game sensitivity to the lowest value.


### Notes

- Buffered Inputs
    - Attack
    - Use
    - Pick Item
    - Scroll up/down
    - Drop Item
    - Swap Hand
    - Inventory
    
    
- Non Buffered Inputs
    - Forward
    - Backward
    - Left
    - Right
    - Jump
    - Sneak
    - Sprint
    - Hotbar
    
This mod is a heavy work in progress. Expect things to be buggy and have limited functionality for the time being.
