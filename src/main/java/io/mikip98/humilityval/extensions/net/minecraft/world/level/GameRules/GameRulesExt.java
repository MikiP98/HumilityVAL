package io.mikip98.humilityval.extensions.net.minecraft.world.level.GameRules;

#if MC_VERSION <= 12111
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.Nullable;

@Extension
public class GameRulesExt {
    public static <T extends GameRules.Value<T>> void set(@This GameRules gameRules, GameRules.Key<T> gameRule, T object, @Nullable MinecraftServer minecraftServer) {
        GameRules.Value<T> virtualGameRule = gameRules.getRule(gameRule);
        virtualGameRule.setFrom(object, minecraftServer);
    }
//    public static <T extends GameRules.Value<T>> T get(@This GameRules gameRules, GameRules.Key<T> gameRule) {
//        GameRules.Value<T> virtualGameRule = gameRules.getRule(gameRule);
//        return virtualGameRule.get();  // Not possible, 'GameRules.Value<T>' does not store the value...
//    }
}
#endif
