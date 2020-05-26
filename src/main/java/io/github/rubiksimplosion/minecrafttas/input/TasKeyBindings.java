package io.github.rubiksimplosion.minecrafttas.input;

import io.github.rubiksimplosion.minecrafttas.util.MouseUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TasKeyBindings {
    public static FabricKeyBinding keyTasTest = FabricKeyBinding.Builder.create(new Identifier("minecrafttas", "test"),
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "tas").build();

    public static void registerKeys() {
        KeyBindingRegistry.INSTANCE.addCategory("tas");
        KeyBindingRegistry.INSTANCE.register(keyTasTest);
        ClientTickCallback.EVENT.register(e ->
        {
            if(keyTasTest.wasPressed()) {
                onKeyTasTestPressed();

            }
        });
    }

    public static void onKeyTasTestPressed() {
//        FakeMouse.fakeMouseButton(0, 1, 0);
//        FakeMouse.fakeMouseButton(0,0,0);
        FakeMouse.fakeCursorMove(MouseUtil.findXFromYaw(80), MouseUtil.findYFromPitch(40));
    }

}
