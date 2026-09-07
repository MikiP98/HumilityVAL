package io.mikip98.humilityval.framework.registrars;

import io.mikip98.humilityval.registries.CreativeModeTabRegistry;
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
