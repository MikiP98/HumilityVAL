#if MC_VERSION < 12004
package io.mikip98.humilityval.extensions.net.minecraft.world.level.block.state.BlockBehaviour;

import manifold.ext.rt.api.Extension;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

@Extension
public class BlockBehaviourExt {
    public static class Properties {
        @Extension
        public static BlockBehaviour.Properties ofFullCopy(Block block) {
            return BlockBehaviour.Properties.copy(block);
        }
    }
}
#endif