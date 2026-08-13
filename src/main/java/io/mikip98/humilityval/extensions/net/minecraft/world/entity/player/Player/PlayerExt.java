package io.mikip98.humilityval.extensions.net.minecraft.world.entity.player.Player;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

@Extension
public class PlayerExt {
    /**
     * Sends a chat message to the player, executes only if called on the logical server.
     */
    public static void avlSendServerMessage(@This Player player, String message) {
        avlSendServerMessage(player, Component.literal(message));
    }
    /**
     * Sends a chat message to the player, executes only if called on the logical server.
     */
    @SuppressWarnings("resource")
    public static void avlSendServerMessage(@This Player player, Component message) {
        if (!player.level().isClientSide()) {
            player.avlSendMessage(message);
        }
    }

    /**
     * Sends a chat message to the player, executes only if called on the logical client.
     */
    public static void avlSendClientMessage(@This Player player, String message) {
        player.avlSendClientMessage(Component.literal(message));
    }
    /**
     * Sends a chat message to the player, executes only if called on the logical client.
     */
    @SuppressWarnings("resource")
    public static void avlSendClientMessage(@This Player player, Component message) {
        if (player.level().isClientSide()) {
            player.avlSendMessage(message);
        }
    }

    /**
     * Sends a chat message to the player, executes on both logical client and logical server.
     */
    public static void avlSendMessage(@This Player player, String message) {
        player.avlSendMessage(Component.literal(message));
    }
    public static void avlSendMessage(@This Player player, Component message) {
        #if MC_VERSION < 260000
        player.displayClientMessage(message, false);
        #else
        player.sendSystemMessage(message);
        #endif
    }
}
