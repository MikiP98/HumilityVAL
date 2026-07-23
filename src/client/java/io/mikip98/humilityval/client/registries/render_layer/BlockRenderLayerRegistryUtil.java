package io.mikip98.humilityval.client.registries.render_layer;

import io.netty.util.internal.UnstableApi;
#if MC_VERSION < 260000 import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap; #endif
#if MC_VERSION < 12105
import net.minecraft.client.renderer.RenderType;
#else
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
#endif
import net.minecraft.world.level.block.Block;

public final class BlockRenderLayerRegistryUtil {
    public static void putBlocksInTranslucent(Block... blocks) {
        #if MC_VERSION < 12105
        applyRenderLayer(RenderType.translucent(), blocks);
        #elif MC_VERSION < 260000
        applyRenderLayer(ChunkSectionLayer.TRANSLUCENT, blocks);
        #endif
    }
    public static void putBlocksInCutoutMipped(Block... blocks) {
        #if MC_VERSION < 12105
        applyRenderLayer(RenderType.cutoutMipped(), blocks);
        #elif MC_VERSION < 12111
        applyRenderLayer(ChunkSectionLayer.CUTOUT_MIPPED, blocks);
        #elif MC_VERSION < 260000
        applyRenderLayer(ChunkSectionLayer.CUTOUT, blocks);
        #endif
    }
    public static void putBlocksInCutout(Block... blocks) {
        #if MC_VERSION < 12105
        applyRenderLayer(RenderType.cutout(), blocks);
        #elif MC_VERSION < 260000
        applyRenderLayer(ChunkSectionLayer.CUTOUT, blocks);
        #endif
    }


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
