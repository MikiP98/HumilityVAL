package io.mikip98.humilityval.extensions.net.minecraft.world.level.Level;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.minecraft.world.level.Level;

@Extension
public class LevelExt {
    /**
     * @return The absolute highest Y coordinate where a block can be placed.
     */
    public static int avlGetTopBuildLimit(@This Level level) {
        #if MC_VERSION < 12104
        return level.getMaxBuildHeight() - 1;
        #else
        return level.getMaxY();
        #endif
    }

    /**
     * @return The absolute lowest Y coordinate where a block can be placed.
     */
    public static int avlGetBottomBuildLimit(@This Level level) {
        #if MC_VERSION < 12104
        return level.getMinBuildHeight();
        #else
        return level.getMinY();
        #endif
    }
}