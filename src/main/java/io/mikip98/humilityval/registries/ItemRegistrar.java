package io.mikip98.humilityval.registries;

#if MC_VERSION < 12104
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
#else
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
#endif
import net.minecraft.world.item.Item;
#if MC_VERSION >= 12104 && MC_VERSION < 260000 import net.minecraft.world.item.Items; #endif

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A Minecraft version-agnostic registry handler for {@link Item}s.
 */
public class ItemRegistrar extends Registrar {
    public ItemRegistrar(String modId) {
        super(modId);
    }


    /**
     * Registers a standalone {@link Item}.
     * @param name Internal registry name (ID) for the item.
     * @return The registered item.
     */
    public Item register(String name) {
        return register(name, Item::new);
    }

    /**
     * Registers a standalone {@link Item}.
     * @param name                    Internal registry name (ID) for the item.
     * @param itemPropertiesSupplier  Supplier providing the item's unique (not shared) properties.
     * @return The registered item.
     */
    public Item register(String name, Supplier<Item.Properties> itemPropertiesSupplier) {
        return register(name, itemPropertiesSupplier.get());
    }

    /**
     * Registers a standalone {@link Item}.
     * @param name            Internal registry name (ID) for the item.
     * @param itemProperties  Unique properties for the item (must not be shared).
     * @return The registered item.
     */
    public Item register(String name, Item.Properties itemProperties) {
        return register(name, Item::new, itemProperties);
    }

    /**
     * Registers a standalone {@link Item}.
     * @param name         Internal registry name (ID) for the item.
     * @param itemFactory  Constructor or factory to instantiate the item.
     * @return The registered item.
     */
    public <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory) {
        return register(name, itemFactory, new Item.Properties());
    }

    /**
     * Registers a standalone {@link Item}.
     * @param name                    Internal registry name (ID) for the item.
     * @param itemFactory             Constructor or factory to instantiate the item.
     * @param itemPropertiesSupplier  Supplier providing the item's unique (not shared) properties.
     * @return The registered item.
     */
    public <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Supplier<Item.Properties> itemPropertiesSupplier) {
        return register(name, itemFactory, itemPropertiesSupplier.get());
    }


    /**
     * Registers a standalone {@link Item}.
     * @param name            Internal registry name (ID) for the item.
     * @param itemFactory     Constructor or factory to instantiate the item.
     * @param itemProperties  Unique properties for the item (must not be shared).
     * @return The registered item.
     */
    #if MC_VERSION >= 12104 @SuppressWarnings("unchecked") #endif
    public <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties itemProperties) {
        #if MC_VERSION < 12104
        return Registry.register(BuiltInRegistries.ITEM, getId(name), itemFactory.apply(itemProperties));
        #else
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, getId(name));
        return (T) #if MC_VERSION < 260000 Items. #endif registerItem(registryKey, (Function<Item.Properties, Item>) itemFactory, itemProperties);
        #endif
    }

    #if MC_VERSION > 260000
    @SuppressWarnings("unchecked")
    protected Item registerItem(final ResourceKey<Item> key, final Function<Item.Properties, Item> itemFactory, final Item.Properties properties) {
        Item item = itemFactory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
    #endif
}
