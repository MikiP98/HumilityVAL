package io.mikip98.humilityval.client.registries;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
#if MC_VERSION >= 12111 import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState; #endif
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class BlockEntityRendererRegistryUtil {
    #if MC_VERSION < 12111
    public static <T extends BlockEntity> void register(BlockEntityType<T> blockEntityType, BlockEntityRendererProvider<T> provider) {
    #else
    public static <T extends BlockEntity, Y extends BlockEntityRenderState> void register(BlockEntityType<T> blockEntityType, BlockEntityRendererProvider<T, Y> provider) {
    #endif
        BlockEntityRenderers.register(blockEntityType, provider);
    }
}
