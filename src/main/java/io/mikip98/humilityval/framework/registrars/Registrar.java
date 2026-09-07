package io.mikip98.humilityval.framework.registrars;

import io.mikip98.humilityval.Util;
import lombok.Getter;
#if MC_VERSION < 12111 import net.minecraft.resources.ResourceLocation; #endif
#if MC_VERSION >= 12111 import net.minecraft.resources.Identifier; #endif
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public abstract class Registrar {
    @Getter private final String modId;

    public Registrar(String modId) {
        this.modId = modId;
    }
    public Registrar() {
        this.modId = null;
    }

    protected #if MC_VERSION < 12111 ResourceLocation #else
    Identifier #endif getId(String name) {
        return Util.getId(getModId(), name);
    }
}
