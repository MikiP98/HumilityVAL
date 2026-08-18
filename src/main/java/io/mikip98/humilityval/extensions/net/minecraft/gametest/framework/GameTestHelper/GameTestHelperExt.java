package io.mikip98.humilityval.extensions.net.minecraft.gametest.framework.GameTestHelper;

#if MC_VERSION >= 12108
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;

@Extension
public class GameTestHelperExt {
    public static void assertTrue(@This GameTestHelper gameTestHelper, boolean bl, String string) {
        gameTestHelper.assertTrue(bl, Component.literal(string));
    }
    public static void fail(@This GameTestHelper gameTestHelper, String string) {
        gameTestHelper.fail(Component.literal(string));
    }
}
#endif
