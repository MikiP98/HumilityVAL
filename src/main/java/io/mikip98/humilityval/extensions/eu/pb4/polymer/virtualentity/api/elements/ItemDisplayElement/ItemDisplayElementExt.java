package io.mikip98.humilityval.extensions.eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;

import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.minecraft.world.item.ItemDisplayContext;

// Having this package or imports inside the #if check crashes manifold, TODO: Report this

#if POLYMER && MC_VERSION < 12108
@Extension
public class ItemDisplayElementExt {
    public static void setItemDisplayContext(@This ItemDisplayElement display, ItemDisplayContext context) {
        display.setModelTransformation(context);
    }
}
#endif