package io.mikip98.humilityval.framework.registrars;

#if MC_VERSION < 260000
import io.mikip98.humilityval.registries.CreativeModeTabRegistry;
#else
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
#endif
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Consumer;

public class CreativeModeTabRegistrar extends Registrar {
    public CreativeModeTabRegistrar(String modId) {
        super(modId);
    }

    // TODO: Consider creating a static version in core
    protected CreativeModeTab createAndRegisterCreativeModeTab(String name, Consumer<CreativeModeTab.Builder> configuration) {
        CreativeModeTab.Builder builder = CreativeModeTabRegistry.builder();
        builder.title(Component.translatable(getModId() + ".itemGroup." + name));
        configuration.accept(builder);
        return CreativeModeTabRegistry.register(getId(name), builder.build());
    }
}
