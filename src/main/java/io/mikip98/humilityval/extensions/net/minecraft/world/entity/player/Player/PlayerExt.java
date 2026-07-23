package io.mikip98.humilityval.extensions.net.minecraft.world.entity.player.Player;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

@Extension
public class PlayerExt {
    public static void avlDisplayClientMessage(@This Player player, String message) {
        player.avlDisplayClientMessage(Component.literal(message));
    }
    public static void avlDisplayClientMessage(@This Player player, Component message) {
        #if MC_VERSION < 260000
        player.displayClientMessage(message, false);
        #else
        player.sendSystemMessage(message);
        #endif
    }
}
