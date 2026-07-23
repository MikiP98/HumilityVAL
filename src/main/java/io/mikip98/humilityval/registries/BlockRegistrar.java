package io.mikip98.humilityval.registries;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
#if MC_VERSION >= 12104
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
#endif
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
#if MC_VERSION >= 12104
import net.minecraft.world.level.block.Blocks;
#endif
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistrar extends Registrar {
    protected final ItemRegistrar itemRegistrar;
    public BlockRegistrar(String modId, ItemRegistrar itemRegistrar) {
        super(modId);
        this.itemRegistrar = itemRegistrar;
    }


    public Block registerWithItem(String name) {
        return registerWithItem(name, BlockBehaviour.Properties::of);
    }
    public Block registerWithItem(String name, Supplier<BlockBehaviour.Properties> settingsSupplier) {
        return registerWithItem(name, settingsSupplier.get());
    }
    public Block registerWithItem(String name, BlockBehaviour.Properties settings) {
        return registerWithItem(name, Block::new, settings);
    }
    public <T extends Block> T registerWithItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> settingsSupplier) {
        return registerWithItem(name, blockFactory, settingsSupplier.get());
    }
    public <T extends Block> T registerWithItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties settings) {
        final T block = register(name, blockFactory, settings);
        itemRegistrar.register(name, itemSettings -> new BlockItem(block, itemSettings));
        return block;
    }

    public Block registerWithItem(String name, Item.Properties itemProperties) {
        return registerWithItem(name, Block.Properties::of, itemProperties);
    }
    public Block registerWithItem(String name, Supplier<BlockBehaviour.Properties> settingsSupplier, Item.Properties itemProperties) {
        return registerWithItem(name, settingsSupplier.get(), itemProperties);
    }
    public Block registerWithItem(String name, BlockBehaviour.Properties settings, Item.Properties itemProperties) {
        return registerWithItem(name, Block::new, settings, itemProperties);
    }
    public <T extends Block> T registerWithItem(
            String name,
            Function<BlockBehaviour.Properties, T> blockFactory,
            Supplier<BlockBehaviour.Properties> settingsSupplier,
            Item.Properties itemProperties
    ) {
        return registerWithItem(name, blockFactory, settingsSupplier.get(), itemProperties);
    }
    public <T extends Block> T registerWithItem(
            String name,
            Function<BlockBehaviour.Properties, T> blockFactory,
            BlockBehaviour.Properties blockProperties,
            Item.Properties itemProperties
    ) {
        final T block = register(name, blockFactory, blockProperties);
        itemRegistrar.register(name, itemSettings -> new BlockItem(block, itemSettings), itemProperties);
        return block;
    }


    public Block register(String name) {
        return register(name, Block.Properties::of);
    }
    public Block register(String name, Supplier<BlockBehaviour.Properties> settingsSupplier) {
        return register(name, settingsSupplier.get());
    }
    public Block register(String name, BlockBehaviour.Properties settings) {
        return register(name, Block::new, settings);
    }
    public <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> settingsSupplier) {
        return register(name, blockFactory, settingsSupplier.get());
    }
    #if MC_VERSION >= 12104 @SuppressWarnings("unchecked") #endif
    public <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties settings) {
        #if MC_VERSION < 12104
        return Registry.register(BuiltInRegistries.BLOCK, getId(name), blockFactory.apply(settings));
        #else
        final ResourceKey<Block> registryKey = ResourceKey.create(Registries.BLOCK, getId(name));
        return (T) Blocks.register(registryKey, (Function<BlockBehaviour.Properties, Block>) blockFactory, settings);
        #endif
    }


    public void registerFlammable(Block block, int burnTime, int spreadSpeed) {
        FlammableBlockRegistry.getDefaultInstance().add(block, burnTime, spreadSpeed);
    }
}
