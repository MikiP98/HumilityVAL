package io.mikip98.humilityval.content.block.entity;

import net.minecraft.core.BlockPos;
#if MC_VERSION >= 12006 && MC_VERSION < 12105 import net.minecraft.core.HolderLookup; #endif
#if MC_VERSION < 12105 import net.minecraft.nbt.CompoundTag; #endif
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
#if MC_VERSION >= 12105 import net.minecraft.world.level.storage.ValueInput; #endif
#if MC_VERSION >= 12105 import net.minecraft.world.level.storage.ValueOutput; #endif

public abstract class AVLBlockEntity extends BlockEntity {
    public AVLBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    /**
     * Replaces vanilla's {@code saveAdditional} method. <br>
     * Writes custom block entity data to the provided output.
     */
    protected abstract void avlSaveAdditional(AVLDataOutput out);

    /**
     * Replaces vanilla's {@code load} or {@code loadAdditional} method depending on the Minecraft version. <br>
     * Reads custom block entity data from the provided input.
     */
    protected abstract void avlLoadAdditional(AVLDataInput in);


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
