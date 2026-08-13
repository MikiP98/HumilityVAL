package io.mikip98.humilityval.client.registries;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
#if MC_VERSION >= 12111 import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState; #endif
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Utility class for registering Block Entity Renderers.
 */
public final class BlockEntityRendererRegistryUtil {
    /**
     * Registers a Block Entity Renderer provider to a Block Entity Type.
     * <br> The method signature changes across MC versions to match the required type of 'BlockEntityRendererProvider'.
     *
     * <p>  - Below 1.21.11: {@code <T extends BlockEntity> BlockEntityRendererProvider<T>}
     * <br> - 1.21.11 and above: {@code <T extends BlockEntity, Y extends BlockEntityRenderState> BlockEntityRendererProvider<T, Y>}
     */
    #if MC_VERSION < 12111
    public static <T extends BlockEntity> void register(BlockEntityType<T> blockEntityType, BlockEntityRendererProvider<T> provider) {
    #else
    public static <T extends BlockEntity, Y extends BlockEntityRenderState> void register(BlockEntityType<T> blockEntityType, BlockEntityRendererProvider<T, Y> provider) {
    #endif
        BlockEntityRenderers.register(blockEntityType, provider);
    }
}
