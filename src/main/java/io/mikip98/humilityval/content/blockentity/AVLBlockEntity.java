package io.mikip98.humilityval.content.blockentity;

#if POLYMER import io.mikip98.humilityval.content.block.entity.polymer.PolymerBlockEntity; #endif
import net.minecraft.core.BlockPos;
#if MC_VERSION >= 12006 && MC_VERSION < 12105 import net.minecraft.core.HolderLookup; #endif
#if MC_VERSION < 12105 import net.minecraft.nbt.CompoundTag; #endif
#if !POLYMER import net.minecraft.world.level.block.entity.BlockEntity; #endif
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
#if MC_VERSION >= 12105 import net.minecraft.world.level.storage.ValueInput; #endif
#if MC_VERSION >= 12105 import net.minecraft.world.level.storage.ValueOutput; #endif

public abstract class AVLBlockEntity extends #if POLYMER PolymerBlockEntity #else BlockEntity #endif {
    public AVLBlockEntity(
            BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState
            #if POLYMER, PolymerProperties properties #endif
    ) {
        super(blockEntityType, blockPos, blockState #if POLYMER, properties #endif);
    }

    /**
     * Replaces vanilla's {@code saveAdditional} method. <br>
     * Writes custom block entity data to the provided output.
     */
    protected void avlSaveAdditional(AVLDataOutput out) {}

    /**
     * Replaces vanilla's {@code load} or {@code loadAdditional} method depending on the Minecraft version. <br>
     * Reads custom block entity data from the provided input.
     */
    protected void avlLoadAdditional(AVLDataInput in) {}


    #if MC_VERSION < 12006
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        avlLoadAdditional(new AVLDataInput(tag));
    }
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        avlSaveAdditional(new AVLDataOutput(tag));
    }

    #elif MC_VERSION < 12105
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        avlLoadAdditional(new AVLDataInput(tag, registries));
    }
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        avlSaveAdditional(new AVLDataOutput(tag, registries));
    }

    #else  // 1.21.5+
    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        avlLoadAdditional(new AVLDataInput(input));
    }
    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        this.avlSaveAdditional(new AVLDataOutput(output));
    }
    #endif
}
