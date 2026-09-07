package io.mikip98.humilityval.registries;

import io.mikip98.humilityval.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
#if MC_VERSION < 260000 import net.minecraft.resources.ResourceLocation; #endif
#if MC_VERSION >= 260000 import net.minecraft.resources.Identifier; #endif
#if MC_VERSION >= 12104 import net.minecraft.core.registries.Registries; #endif
#if MC_VERSION >= 12104 import net.minecraft.resources.ResourceKey; #endif
#if MC_VERSION >= 260000 import net.minecraft.world.item.BlockItem; #endif
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ItemRegistry {
    /**
     * Registers a standalone {@link Item}.
     * @param modId           Registry namespace for the item.
     * @param name            Internal registry name for the item.
     * @param itemFactory     Constructor or factory to instantiate the item.
     * @param itemProperties  Unique properties for the item (must not be shared).
     * @return The registered item.
     */
    #if MC_VERSION >= 12104 @SuppressWarnings("unchecked") #endif
    public static <T extends Item> T register(
            String modId, String name, Function<Item.Properties, T> itemFactory, Item.Properties itemProperties
    ) {
        return register(Util.getId(modId, name), itemFactory, itemProperties);
    }

    /**
     * Registers a standalone {@link Item}.
     * @param itemFactory     Constructor or factory to instantiate the item.
     * @param itemProperties  Unique properties for the item (must not be shared).
     * @return The registered item.
     */
    #if MC_VERSION >= 12104 @SuppressWarnings("unchecked") #endif
    public static <T extends Item> T register(
            #if MC_VERSION < 260000 ResourceLocation #else Identifier #endif id,
            Function<Item.Properties, T> itemFactory,
            Item.Properties itemProperties
    ) {
        #if MC_VERSION < 12104
        return Registry.register(BuiltInRegistries.ITEM, id, itemFactory.apply(itemProperties));
        #else
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, id);
        return (T) #if MC_VERSION < 260000 Items. #endif registerItem(registryKey, (Function<Item.Properties, Item>) itemFactory, itemProperties);
        #endif
    }

    #if MC_VERSION > 260000
    @SuppressWarnings("unchecked")
    protected static Item registerItem(
    final ResourceKey<Item> key, final Function<Item.Properties, Item> itemFactory, final Item.Properties properties
    ) {
        Item item = itemFactory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
    #endif
}
