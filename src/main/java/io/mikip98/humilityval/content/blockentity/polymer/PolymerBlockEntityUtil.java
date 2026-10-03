#if POLYMER
package io.mikip98.humilityval.content.block.entity.polymer;

#if MC_VERSION < 12104 import eu.pb4.polymer.resourcepack.api.PolymerModelData; #endif
#if MC_VERSION >= 12005 import net.minecraft.core.component.DataComponents; #endif
#if MC_VERSION >= 12104 && MC_VERSION < 12111 import net.minecraft.resources.ResourceLocation; #endif
#if MC_VERSION >= 12111 import net.minecraft.resources.Identifier; #endif
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
#if MC_VERSION >= 12005 && MC_VERSION < 12104 import net.minecraft.world.item.component.CustomModelData; #endif

public class PolymerBlockEntityUtil {
    public static ItemStack disguiseItem(Item itemBase, #if MC_VERSION < 12104 PolymerModelData #elif MC_VERSION < 12111 ResourceLocation #else Identifier #endif customModel) {
        final ItemStack disguisedStack = new ItemStack(itemBase);

        if (customModel != null) {
            #if MC_VERSION < 12005
            // 1.20.1 - 1.20.4: NBT Tags
            disguisedStack.getOrCreateTag().putInt("CustomModelData", customModel.value());
            #elif MC_VERSION < 12104
            // 1.20.5 - 1.21.1: Component Data (Integer)
            disguisedStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(customModel.value()));
            #else
            // 1.21.4+: Item Model Component (ResourceLocation/Identifier)
            disguisedStack.set(DataComponents.ITEM_MODEL, customModel);
            #endif
        }

        return disguisedStack;
    }
}
#endif