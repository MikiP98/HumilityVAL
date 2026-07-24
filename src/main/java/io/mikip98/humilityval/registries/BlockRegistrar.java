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

    public BlockRegistrar(String modId) {
        super(modId);
        this.itemRegistrar = new ItemRegistrar(modId);
    }
    public BlockRegistrar(String modId, ItemRegistrar itemRegistrar) {
        super(modId);
        this.itemRegistrar = itemRegistrar;
    }

    public Block registerWithItem(String name) {
        return registerWithItem(name, BlockBehaviour.Properties::of);
    }
    public Block registerWithItem(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier) {
        return registerWithItem(name, blockPropertiesSupplier.get());
    }
    public Block registerWithItem(String name, BlockBehaviour.Properties blockProperties) {
        return registerWithItem(name, Block::new, blockProperties);
    }
    public <T extends Block> T registerWithItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier) {
        return registerWithItem(name, blockFactory, blockPropertiesSupplier.get());
    }
    public <T extends Block> T registerWithItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties) {
        final T block = register(name, blockFactory, blockProperties);
        itemRegistrar.register(name, itemProperties -> new BlockItem(block, itemProperties));
        return block;
    }

    // TODO: Consider adding the ability to pass a custom BlockItem factory
    public Block registerWithCustomItem(String name, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, Block.Properties::of, itemPropertiesSupplier.get());
    }
    public Block registerWithCustomItem(String name, Item.Properties itemProperties) {
        return registerWithCustomItem(name, Block.Properties::of, itemProperties);
    }
    public Block registerWithCustomItem(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, blockPropertiesSupplier.get(), itemPropertiesSupplier.get());
    }
    public Block registerWithCustomItem(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, Item.Properties itemProperties) {
        return registerWithCustomItem(name, blockPropertiesSupplier.get(), itemProperties);
    }
    public Block registerWithCustomItem(String name, BlockBehaviour.Properties blockProperties, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, Block::new, blockProperties, itemPropertiesSupplier.get());
    }
    public Block registerWithCustomItem(String name, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        return registerWithCustomItem(name, Block::new, blockProperties, itemProperties);
    }
    public <T extends Block> T registerWithCustomItem(
            String name,
            Function<BlockBehaviour.Properties, T> blockFactory,
            Supplier<BlockBehaviour.Properties> blockPropertiesSupplier,
            Supplier<Item.Properties> itemPropertiesSupplier
    ) {
        return registerWithCustomItem(name, blockFactory, blockPropertiesSupplier.get(), itemPropertiesSupplier.get());
    }
    public <T extends Block> T registerWithCustomItem(
            String name,
            Function<BlockBehaviour.Properties, T> blockFactory,
            Supplier<BlockBehaviour.Properties> blockPropertiesSupplier,
            Item.Properties itemProperties
    ) {
        return registerWithCustomItem(name, blockFactory, blockPropertiesSupplier.get(), itemProperties);
    }
    public <T extends Block> T registerWithCustomItem(
            String name,
            Function<BlockBehaviour.Properties, T> blockFactory,
            BlockBehaviour.Properties blockProperties,
            Supplier<Item.Properties> itemPropertiesSupplier
    ) {
        return registerWithCustomItem(name, blockFactory, blockProperties, itemPropertiesSupplier.get());
    }
    public <T extends Block> T registerWithCustomItem(
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
    public Block register(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier) {
        return register(name, blockPropertiesSupplier.get());
    }
    public Block register(String name, BlockBehaviour.Properties blockProperties) {
        return register(name, Block::new, blockProperties);
    }
    public <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier) {
        return register(name, blockFactory, blockPropertiesSupplier.get());
    }
    #if MC_VERSION >= 12104 @SuppressWarnings("unchecked") #endif
    public <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties) {
        #if MC_VERSION < 12104
        return Registry.register(BuiltInRegistries.BLOCK, getId(name), blockFactory.apply(blockProperties));
        #else
        final ResourceKey<Block> registryKey = ResourceKey.create(Registries.BLOCK, getId(name));
        return (T) Blocks.register(registryKey, (Function<BlockBehaviour.Properties, Block>) blockFactory, blockProperties);
        #endif
    }


    public void registerFlammable(Block block, int burnTime, int spreadSpeed) {
        FlammableBlockRegistry.getDefaultInstance().add(block, burnTime, spreadSpeed);
    }
}
