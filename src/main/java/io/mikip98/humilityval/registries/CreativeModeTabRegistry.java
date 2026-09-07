package io.mikip98.humilityval.registries;

#if MC_VERSION < 260000 import io.mikip98.humilityval.Util;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup; #endif
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
#if MC_VERSION < 260000 import net.minecraft.resources.ResourceLocation; #endif
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CreativeModeTabRegistry {
    public static CreativeModeTab.Builder builder() {
        return #if MC_VERSION < 260000 FabricItemGroup #else FabricCreativeModeTab #endif.builder();
    }

    public static CreativeModeTab register(String modId, String name, CreativeModeTab tab) {
        return register(Util.getId(modId, name), tab);
    }
    public static CreativeModeTab register(#if MC_VERSION < 260000 ResourceLocation #else Identifier #endif id, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, tab);
    }

    @SafeVarargs
    protected static void putIntoCreativeModeTab(Collection<? extends ItemLike> items, ResourceKey<CreativeModeTab>... itemGroups) {
        for (ResourceKey<CreativeModeTab> itemGroup : itemGroups) {
            #if MC_VERSION < 260000
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(
                    content -> items.forEach(content::accept)
            );
            #else
            CreativeModeTabEvents.modifyOutputEvent(itemGroup).register(
                    creativeTab -> items.forEach(creativeTab::accept)
            );
            #endif
        }
    }
    @SafeVarargs
    protected static void putIntoCreativeModeTab(ItemLike[] items, ResourceKey<CreativeModeTab>... itemGroups) {
        putIntoCreativeModeTab(Arrays.asList(items), itemGroups);
    }
    @SafeVarargs
    protected static void putIntoCreativeModeTab(ItemLike item, ResourceKey<CreativeModeTab>... itemGroups) {
        putIntoCreativeModeTab(List.of(item), itemGroups);
    }

    protected static void putIntoCreativeModeTab(ResourceKey<CreativeModeTab> itemGroup, ItemLike... items) {
        putIntoCreativeModeTab(Arrays.asList(items), itemGroup);
    }
}
