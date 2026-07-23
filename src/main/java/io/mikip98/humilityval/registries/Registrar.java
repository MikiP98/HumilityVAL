package io.mikip98.humilityval.registries;

#if MC_VERSION < 12111
import net.minecraft.resources.ResourceLocation;
#else
import net.minecraft.resources.Identifier;
#endif

public abstract class Registrar {
    private final String modId;
    public Registrar(String modId) {
        this.modId = modId;
    }

    public #if MC_VERSION < 12111 ResourceLocation #else Identifier #endif getId(String name) {
        final #if MC_VERSION < 12111 ResourceLocation #else Identifier #endif id = #if MC_VERSION < 12111 ResourceLocation #else Identifier #endif .tryBuild(modId , name);
        if (id == null) throw new IllegalArgumentException("Broken block id: " + modId + ":" + name);
        return id;
    }
}
