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

public class ItemRegistrar extends Registrar {
    public ItemRegistrar(String modId) {
        super(modId);
    }

    public Item register(String name) {
        return register(name, Item::new);
    }
    public <T extends Item> T register(String name, Function<Item.Properties, T> factory) {
        return register(name, factory, new Item.Properties());
    }

    #if MC_VERSION < 12104
    public <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties settings) {
        return Registry.register(BuiltInRegistries.ITEM, getId(name), factory.apply(settings));
    }
    #else
    public <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties settings) {
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, getId(name));
        return #if MC_VERSION < 260000 Items. #endif registerItem(registryKey, factory, settings);
    }
    #endif

    #if MC_VERSION > 260000
    @SuppressWarnings("unchecked")
    protected <T extends Item> T registerItem(final ResourceKey<Item> key, final Function<Item.Properties, T> itemFactory, final Item.Properties properties) {
        Item item = itemFactory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
        return (T) Registry.register(BuiltInRegistries.ITEM, key, item);
    }
    #endif
}
