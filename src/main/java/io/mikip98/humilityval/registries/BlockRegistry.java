package io.mikip98.humilityval.registries;

import io.mikip98.humilityval.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
#if MC_VERSION < 260000 import net.minecraft.resources.ResourceLocation; #endif
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class BlockRegistry {
    /**
     * Registers a standalone {@link Block}.
     * @param modId            Registry namespace for the block.
     * @param name             Internal registry name for the block.
     * @param blockFactory     Constructor or factory to instantiate the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @return The registered block.
     */
    public static <T extends Block> T register(String modId, String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties) {
        return register(Util.getId(modId, name), blockFactory, blockProperties);
    }

    /**
     * Registers a standalone {@link Block}.
     * @param blockFactory     Constructor or factory to instantiate the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @return The registered block.
     */
    #if MC_VERSION >= 12104 @SuppressWarnings("unchecked") #endif
    public static <T extends Block> T register(
            #if MC_VERSION < 260000 ResourceLocation #else Identifier #endif id,
            Function<BlockBehaviour.Properties, T> blockFactory,
            BlockBehaviour.Properties blockProperties
    ) {
        #if MC_VERSION < 12104
        return Registry.register(BuiltInRegistries.BLOCK, id, blockFactory.apply(blockProperties));
        #else
        final ResourceKey<Block> registryKey = ResourceKey.create(Registries.BLOCK, id);
        return (T) Blocks.register(registryKey, (Function<BlockBehaviour.Properties, Block>) blockFactory, blockProperties);
        #endif
    }
}
