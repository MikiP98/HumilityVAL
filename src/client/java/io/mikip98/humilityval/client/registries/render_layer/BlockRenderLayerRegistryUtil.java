package io.mikip98.humilityval.client.registries.render_layer;

import io.netty.util.internal.UnstableApi;
#if MC_VERSION < 12105 import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap; #endif
#if MC_VERSION < 12105
import net.minecraft.client.renderer.RenderType;
#else
#if MC_VERSION >= 12105 && MC_VERSION < 260000 import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap; #endif
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
#endif
import net.minecraft.world.level.block.Block;

/**
 * Utility class for registering block render layers (Translucent, Cutout, etc.) before MC version 26.1.
 * <p>Past 26.1, render layers are handled automatically based on the block texture translucency,
 * on those versions these methods execute safely as no-ops.
 */
public final class BlockRenderLayerRegistryUtil {
    /**
     * Before 26.1 assigns blocks to the <b>Translucent</b> layer.
     * <br> Past 26.1 executes a no-op.
     */
    public static void putBlocksInTranslucent(Block... blocks) {
        #if MC_VERSION < 12105
        applyRenderLayer(RenderType.translucent(), blocks);
        #elif MC_VERSION < 260000
        applyRenderLayer(ChunkSectionLayer.TRANSLUCENT, blocks);
        #endif
    }

    /**
     * Before 26.1 assigns blocks to the <b>Cutout Mipped</b> layer.
     * <br> Past 26.1 executes a no-op.
     *
     * @apiNote In 1.21.11+, the CUTOUT_MIPPED layer was removed by Mojang.
     * <br> On these versions, this method silently falls back to standard CUTOUT.
     */
    public static void putBlocksInCutoutMipped(Block... blocks) {
        #if MC_VERSION < 12105
        applyRenderLayer(RenderType.cutoutMipped(), blocks);
        #elif MC_VERSION < 12111
        applyRenderLayer(ChunkSectionLayer.CUTOUT_MIPPED, blocks);
        #elif MC_VERSION < 260000
        applyRenderLayer(ChunkSectionLayer.CUTOUT, blocks);
        #endif
    }

    /**
     * Before 26.1 assigns blocks to the <b>Cutout</b> layer.
     * <br> Past 26.1 executes a no-op.
     */
    public static void putBlocksInCutout(Block... blocks) {
        #if MC_VERSION < 12105
        applyRenderLayer(RenderType.cutout(), blocks);
        #elif MC_VERSION < 260000
        applyRenderLayer(ChunkSectionLayer.CUTOUT, blocks);
        #endif
    }


    /**
     * Directly applies a render layer to blocks.
     * <br> Past 26.1 executes a no-op.
     *
     * @apiNote The first parameter signature changes across versions:
     * <br> - Below 1.21.5: {@code RenderType}
     * <br> - 1.21.5 and above: {@code ChunkSectionLayer}
     */
    @UnstableApi
    #if MC_VERSION < 12105
    public static void applyRenderLayer(RenderType renderLayer, Block... blocks) {
        BlockRenderLayerMap.INSTANCE.putBlocks(renderLayer, blocks);
    }
    #else
    public static void applyRenderLayer(ChunkSectionLayer renderLayer, Block... blocks) {
        #if MC_VERSION < 260000
        BlockRenderLayerMap.putBlocks(renderLayer, blocks);
        #endif
    }
    #endif
}
