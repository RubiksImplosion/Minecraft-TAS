# Minecraft-TAS
A mod for creating tool assisted speedruns of Minecraft.

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
1. Add the commands in chronological order. Each new line is executed 1 tick after the previous line. Example scipts can be found in the scripts folder
1. Load scripts with the `/script set <script name>` command, it will autocomplete the names of valid script files
1. Start and stop scripts with the `/script start` command, or by pressing the start script keybind (default `O`)
1. Start and stop scripts with the `/script stop` command, or by pressing the stop script keybind (default `P`)

### Available commands:
 
 `wait <ticks>` pauses execution of the script for the given number of ticks 
 
`+autojump|-autojump` - Enables automatic jumping the moment the player hits the ground

`yaw <value>` - Sets the player's yaw to the specified value*
 
`pitch <value>` - Sets the player's pitch to the specified value*

`scrollup <value>` - Scrolls the mouse wheel up the given amount

`scrolldown <value>` - Scrolls the mouse wheel down the given amount

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

`+chat|-chat` - Presses and releases that chat key respectively

`+command|-command` - Presses and releases the command key respectively

`+escape|-escape` - Presses and releases the escape key respectively


Insert a semicolon `;` between commands on the same line to execute them both on the same tick
For example, the command `+forward; +sprint` would begin pressing forward and sprint on the same tick.

*Player input has been implemented by directly simulating mouse/keyboard movements. Due to this there can be slight inconsistencies
with things such as player camera movement due to the math related to mouse sensitivity. For the most accurate player camera movement
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
    
This mod is a work in progress. Expect things to be buggy and have limited functionality for the time being.
