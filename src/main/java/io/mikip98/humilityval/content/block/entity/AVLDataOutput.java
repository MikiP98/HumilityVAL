package io.mikip98.humilityval.content.block.entity;

#if MC_VERSION > 12006 && MC_VERSION < 12105 import net.minecraft.core.HolderLookup; #endif
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
#if MC_VERSION < 12105 import net.minecraft.nbt.CompoundTag; #endif
#if MC_VERSION >= 12105 import net.minecraft.core.UUIDUtil; #endif
#if MC_VERSION < 12105 import net.minecraft.nbt.NbtUtils; #endif
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;
#if MC_VERSION >= 12105 import net.minecraft.world.level.storage.ValueOutput; #endif

public class AVLDataOutput {
    #if MC_VERSION < 12105
    protected final CompoundTag tag;
    #if MC_VERSION >= 12006 protected final HolderLookup.Provider registries; #endif
    #else
    protected final ValueOutput output;
    #endif


    #if MC_VERSION < 12006
    public AVLDataOutput(CompoundTag tag) {
        this.tag = tag;
    }
    #elif MC_VERSION < 12105
    public AVLDataOutput(CompoundTag tag, HolderLookup.Provider registries) {
        this.tag = tag;
        this.registries = registries;
    }
    #else
    public AVLDataOutput(ValueOutput output) {
        this.output = output;
    }
    #endif


    #if MC_VERSION < 12105
    public AVLDataOutput putBoolean(String key, boolean value) { tag.putBoolean(key, value); return this; }
    public AVLDataOutput putByte(String key, byte value)       { tag.putByte(key, value); return this; }
    public AVLDataOutput putShort(String key, short value)     { tag.putShort(key, value); return this; }
    public AVLDataOutput putInt(String key, int value)         { tag.putInt(key, value); return this; }
    public AVLDataOutput putLong(String key, long value)       { tag.putLong(key, value); return this; }
    public AVLDataOutput putFloat(String key, float value)     { tag.putFloat(key, value); return this; }
    public AVLDataOutput putDouble(String key, double value)   { tag.putDouble(key, value); return this; }
    public AVLDataOutput putString(String key, String value)   { tag.putString(key, value); return this; }
    public AVLDataOutput putUUID(String key, UUID value)       { tag.putUUID(key, value); return this; }
    public AVLDataOutput putIntArray(String key, int[] value)  { tag.putIntArray(key, value); return this; }
    #else
    public AVLDataOutput putBoolean(String key, boolean value) { output.putBoolean(key, value); return this; }
    public AVLDataOutput putByte(String key, byte value)       { output.putByte(key, value); return this; }
    public AVLDataOutput putShort(String key, short value)     { output.putShort(key, value); return this; }
    public AVLDataOutput putInt(String key, int value)         { output.putInt(key, value); return this; }
    public AVLDataOutput putLong(String key, long value)       { output.putLong(key, value); return this; }
    public AVLDataOutput putFloat(String key, float value)     { output.putFloat(key, value); return this; }
    public AVLDataOutput putDouble(String key, double value)   { output.putDouble(key, value); return this; }
    public AVLDataOutput putString(String key, String value)   { output.putString(key, value); return this; }
    public AVLDataOutput putUUID(String key, UUID value)       { output.store(key, UUIDUtil.CODEC, value); return this; }
    public AVLDataOutput putIntArray(String key, int[] value)  { output.putIntArray(key, value); return this; }
    #endif

    /**
     * Writes a BlockPos as a {@code long}. <br>
     * This is the recommended method for {@code BlockPos} saving.
     */
    public AVLDataOutput putBlockPos(String key, BlockPos value) {
        return putLong(key, value.asLong());
    }


    /**
     * Writes a BlockPos in a {@code Map}-like format. <br>
     * It is recommended to use {@code putBlockPos} instead unless human-readable save data is strictly required.
     */
    public AVLDataOutput putHumanReadableBlockPos(String key, BlockPos value) {
        #if MC_VERSION < 12105
        tag.put(key, NbtUtils.writeBlockPos(value));
        #else
        output.store(key, BlockPos.CODEC, value);
        #endif
        return this;
    }

    /**
     * Saves the contents of the provided list.
     * @apiNote Vanilla hardcodes the save key. To save multiple lists, write them to separate {@link #child(String)} scopes.
     */
    public AVLDataOutput saveItems(NonNullList<ItemStack> items) {
        #if MC_VERSION < 12006
        ContainerHelper.saveAllItems(tag, items);
        #elif MC_VERSION < 12105
        ContainerHelper.saveAllItems(tag, items, registries);
        #else
        ContainerHelper.saveAllItems(output, items, false);
        #endif
        return this;
    }


    public AVLDataOutput child(String key) {
        #if MC_VERSION < 12105
        CompoundTag childTag = new CompoundTag();
        this.tag.put(key, childTag);
        return new AVLDataOutput(childTag #if MC_VERSION >= 12006, registries #endif);
        #else
        return new AVLDataOutput(this.output.child(key));
        #endif
    }


    #if MC_VERSION < 12105
    public CompoundTag getRawTag() { return this.tag; }
    #if MC_VERSION >= 12006 public HolderLookup.Provider getRawRegistryLookup() { return this.registries; } #endif
    #else
    public ValueOutput getRawOutput() { return this.output; }
    #endif
}
