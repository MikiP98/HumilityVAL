package io.mikip98.humilityval;

#if MC_VERSION < 12111 import net.minecraft.resources.ResourceLocation; #endif
#if MC_VERSION >= 12111 import net.minecraft.resources.Identifier; #endif
import org.jetbrains.annotations.ApiStatus;

// TODO: Expose to users
@ApiStatus.Internal
public class Util {
    public static #if MC_VERSION < 12111 ResourceLocation #else Identifier #endif getId(String modId, String name) {
        final #if MC_VERSION < 12111 ResourceLocation #else Identifier #endif id = #if MC_VERSION < 12111 ResourceLocation #else Identifier #endif .tryBuild(modId , name);
        if (id == null) throw new IllegalArgumentException("Broken block id -> " + modId + ":" + name);
        return id;
    }
}
