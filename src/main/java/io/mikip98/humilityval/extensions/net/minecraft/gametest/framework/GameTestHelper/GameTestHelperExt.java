package io.mikip98.humilityval.extensions.net.minecraft.gametest.framework.GameTestHelper;

import manifold.ext.rt.api.Extension;
#if MC_VERSION >= 12108
import manifold.ext.rt.api.This;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
#endif

@Extension
public class GameTestHelperExt {
    #if MC_VERSION >= 12108
    public static void assertTrue(@This GameTestHelper gameTestHelper, boolean bl, String string) {
        gameTestHelper.assertTrue(bl, Component.literal(string));
    }
    #endif
}
