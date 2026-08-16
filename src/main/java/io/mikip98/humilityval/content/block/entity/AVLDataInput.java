package io.mikip98.humilityval.content.block.entity;

#if MC_VERSION >= 12006 && MC_VERSION < 12105 import net.minecraft.core.HolderLookup; #endif
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
#if MC_VERSION < 12105 import net.minecraft.nbt.CompoundTag; #endif
#if MC_VERSION >= 12105 import net.minecraft.core.UUIDUtil; #endif
#if MC_VERSION < 12105 import net.minecraft.nbt.NbtUtils; #endif
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
#if MC_VERSION >= 12105
import net.minecraft.world.level.storage.ValueInput; #endif

import java.util.UUID;

public class AVLDataInput {
    #if MC_VERSION < 12105
    protected final CompoundTag tag;
    #if MC_VERSION >= 12006 protected final HolderLookup.Provider registries; #endif
    #else
    protected final ValueInput input;
    #endif


    #if MC_VERSION < 12006
    public AVLDataInput(CompoundTag tag) {
        this.tag = tag;
    }
    #elif MC_VERSION < 12105
    public AVLDataInput(CompoundTag tag, HolderLookup.Provider registries) {
        this.tag = tag;
        this.registries = registries;
    }
    #else
    public AVLDataInput(ValueInput input) {
        this.input = input;
    }
    #endif


    #if MC_VERSION < 12105
    public boolean getBooleanOr(String key, boolean defaultValue) { return tag.contains(key) ? tag.getBoolean(key) : defaultValue; }
    public byte getByteOr(String key, byte defaultValue)          { return tag.contains(key) ? tag.getByte(key) : defaultValue; }
    public short getShortOr(String key, short defaultValue)       { return tag.contains(key) ? tag.getShort(key) : defaultValue; }
    public int getIntOr(String key, int defaultValue)             { return tag.contains(key) ? tag.getInt(key) : defaultValue; }
    public long getLongOr(String key, long defaultValue)          { return tag.contains(key) ? tag.getLong(key) : defaultValue; }
    public float getFloatOr(String key, float defaultValue)       { return tag.contains(key) ? tag.getFloat(key) : defaultValue; }
    public double getDoubleOr(String key, double defaultValue)    { return tag.contains(key) ? tag.getDouble(key) : defaultValue; }
    public String getStringOr(String key, String defaultValue)    { return tag.contains(key) ? tag.getString(key) : defaultValue; }
    public UUID getUUIDOr(String key, UUID defaultValue)          { return tag.hasUUID(key) ? tag.getUUID(key) : defaultValue; }
    public int[] getIntArrayOr(String key, int[] defaultValue)    { return tag.contains(key) ? tag.getIntArray(key) : defaultValue; }
    #else
    public boolean getBooleanOr(String key, boolean defaultValue) { return input.getBooleanOr(key, defaultValue); }
    public byte getByteOr(String key, byte defaultValue)          { return input.getByteOr(key, defaultValue); }
    public short getShortOr(String key, short defaultValue)       { return (short) input.getShortOr(key, defaultValue); }
    public int getIntOr(String key, int defaultValue)             { return input.getIntOr(key, defaultValue); }
    public long getLongOr(String key, long defaultValue)          { return input.getLongOr(key, defaultValue); }
    public float getFloatOr(String key, float defaultValue)       { return input.getFloatOr(key, defaultValue); }
    public double getDoubleOr(String key, double defaultValue)    { return input.getDoubleOr(key, defaultValue); }
    public String getStringOr(String key, String defaultValue)    { return input.getStringOr(key, defaultValue); }
    public UUID getUUIDOr(String key, UUID defaultValue)          { return input.read(key, UUIDUtil.CODEC).orElse(defaultValue); }
    public int[] getIntArrayOr(String key, int[] defaultValue)    { return input.getIntArray(key).orElse(defaultValue); }
    #endif

    public int[] getIntArrayOrEmpty(String key) { return getIntArrayOr(key, new int[0]); }

    /**
     * Reads a BlockPos saved as a {@code long}. <br>
     * This is the recommended method for {@code BlockPos} loading.
     */
    public BlockPos getBlockPosOr(String key, BlockPos defaultValue) {
        return BlockPos.of(getLongOr(key, defaultValue.asLong()));
    }


    /**
     * Reads a BlockPos saved in a {@code Map}-like format. <br>
     * It is recommended to use {@code getBlockPosOr} instead unless human-readable save data is strictly required.
     */
    public BlockPos getHumanReadableBlockPosOr(String key, BlockPos defaultValue) {
        #if MC_VERSION < 12006
        return tag.contains(key) ? NbtUtils.readBlockPos(tag.getCompound(key)) : defaultValue;
        #elif MC_VERSION < 12105
        return tag.contains(key) ? NbtUtils.readBlockPos(tag, key).get() : defaultValue;
        #else
        return input.read(key, BlockPos.CODEC).orElse(defaultValue);
        #endif
    }

    /**
     * Mutates the provided list by loading saved items into it.
     * @apiNote This will only load items up to the size of the provided list. It does not dynamically resize it.
     * <br> Vanilla hardcodes the read key. To load multiple lists, read them from separate {@link #child(String)} scopes.
     */
    public void loadItems(NonNullList<ItemStack> items) {
        #if MC_VERSION < 12006
        ContainerHelper.loadAllItems(tag, items);
        #elif MC_VERSION < 12105
        ContainerHelper.loadAllItems(tag, items, registries);
        #else
        ContainerHelper.loadAllItems(input, items);
        #endif
    }


    public AVLDataInput child(String key) {
        #if MC_VERSION < 12105
        return new AVLDataInput(tag.getCompound(key) #if MC_VERSION >= 12006, registries #endif);
        #else
        return new AVLDataInput(input.childOrEmpty(key));
        #endif
    }


    #if MC_VERSION < 12105
    public CompoundTag getRawTag() { return this.tag; }
    #if MC_VERSION >= 12006 public HolderLookup.Provider getRawRegistryLookup() { return this.registries; } #endif
    #else
    public ValueInput getRawInput() { return this.input; }
    #endif
}
