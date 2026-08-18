package io.mikip98.humilityval.extensions.net.minecraft.server.MinecraftServer;

#if MC_VERSION == 12111
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRules;

@Extension
public class MinecraftServerExt {
    public static GameRules getGameRules(@This MinecraftServer minecraftServer) {
        return minecraftServer.getWorldData().getGameRules();
    }
}
#endif
