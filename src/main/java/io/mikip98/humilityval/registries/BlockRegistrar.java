package io.mikip98.humilityval.registries;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
#if MC_VERSION < 12104
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
#endif
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

/**
 * A Minecraft version-agnostic registry handler for {@link Block}s and their associated {@link BlockItem}s.
 */
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


    // No item

    /**
     * Registers a standalone {@link Block}.
     * @param name Internal registry name (ID) for the block.
     * @return The registered block.
     */
    public Block register(String name) {
        return register(name, BlockBehaviour.Properties::of);
    }

    /**
     * Registers a standalone {@link Block}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @return The registered block.
     */
    public Block register(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier) {
        return register(name, blockPropertiesSupplier.get());
    }

    /**
     * Registers a standalone {@link Block}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @return The registered block.
     */
    public Block register(String name, BlockBehaviour.Properties blockProperties) {
        return register(name, Block::new, blockProperties);
    }

    /**
     * Registers a standalone {@link Block}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockFactory             Constructor or factory to instantiate the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @return The registered block.
     */
    public <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier) {
        return register(name, blockFactory, blockPropertiesSupplier.get());
    }

    /**
     * Registers a standalone {@link Block}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockFactory     Constructor or factory to instantiate the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @return The registered block.
     */
    #if MC_VERSION >= 12104 @SuppressWarnings("unchecked") #endif
    public <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties) {
        #if MC_VERSION < 12104
        return Registry.register(BuiltInRegistries.BLOCK, getId(name), blockFactory.apply(blockProperties));
        #else
        final ResourceKey<Block> registryKey = ResourceKey.create(Registries.BLOCK, getId(name));
        return (T) Blocks.register(registryKey, (Function<BlockBehaviour.Properties, Block>) blockFactory, blockProperties);
        #endif
    }


    // No custom item factory nor custom item properties

    /**
     * Registers a {@link Block} alongside a standard {@link BlockItem}.
     * @param name Internal registry name (ID) for the block.
     * @return The registered block.
     */
    public Block registerWithItem(String name) {
        return registerWithItem(name, BlockBehaviour.Properties::of);
    }

    /**
     * Registers a {@link Block} alongside a standard {@link BlockItem}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @return The registered block.
     */
    public Block registerWithItem(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier) {
        return registerWithItem(name, blockPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a standard {@link BlockItem}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @return The registered block.
     */
    public Block registerWithItem(String name, BlockBehaviour.Properties blockProperties) {
        return registerWithItem(name, Block::new, blockProperties);
    }

    /**
     * Registers a {@link Block} alongside a standard {@link BlockItem}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockFactory             Constructor or factory to instantiate the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @return The registered block.
     */
    public <T extends Block> T registerWithItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier) {
        return registerWithItem(name, blockFactory, blockPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a standard {@link BlockItem}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockFactory     Constructor or factory to instantiate the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @return The registered block.
     */
    public <T extends Block> T registerWithItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties) {
        final T block = register(name, blockFactory, blockProperties);
        itemRegistrar.register(name, itemProperties -> new BlockItem(block, itemProperties));
        return block;
    }


    // Custom BlockItem factory, no custom item properties

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name         Internal registry name (ID) for the block.
     * @param itemFactory  Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, BlockItemFactory itemFactory) {
        return registerWithCustomItem(name, BlockBehaviour.Properties::of, itemFactory);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemFactory              Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, BlockItemFactory itemFactory) {
        return registerWithCustomItem(name, blockPropertiesSupplier.get(), itemFactory);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @param itemFactory      Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, BlockBehaviour.Properties blockProperties, BlockItemFactory itemFactory) {
        return registerWithCustomItem(name, Block::new, blockProperties, itemFactory);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockFactory             Constructor or factory to instantiate the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemFactory              Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, BlockItemFactory itemFactory) {
        return registerWithCustomItem(name, blockFactory, blockPropertiesSupplier.get(), itemFactory);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockFactory     Constructor or factory to instantiate the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @param itemFactory      Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties, BlockItemFactory itemFactory) {
        final T block = register(name, blockFactory, blockProperties);
        itemRegistrar.register(name, itemSettings -> itemFactory.apply(block, itemSettings));
        return block;
    }

    // No custom factory, but custom item properties
    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                    Internal registry name (ID) for the block.
     * @param itemPropertiesSupplier  Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name            Internal registry name (ID) for the block.
     * @param itemProperties  Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, Item.Properties itemProperties) {
        return registerWithCustomItem(name, BlockBehaviour.Properties::of, itemProperties);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemPropertiesSupplier   Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, blockPropertiesSupplier.get(), itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemProperties           Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, Item.Properties itemProperties) {
        return registerWithCustomItem(name, blockPropertiesSupplier.get(), itemProperties);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                    Internal registry name (ID) for the block.
     * @param blockProperties         Unique properties for the block (must not be shared).
     * @param itemPropertiesSupplier  Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, BlockBehaviour.Properties blockProperties, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, blockProperties, itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @param itemProperties   Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        return registerWithCustomItem(name, Block::new, blockProperties, itemProperties);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockFactory             Constructor or factory to instantiate the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemPropertiesSupplier   Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, blockFactory, blockPropertiesSupplier.get(), itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockFactory             Constructor or factory to instantiate the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemProperties           Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, Item.Properties itemProperties) {
        return registerWithCustomItem(name, blockFactory, blockPropertiesSupplier.get(), itemProperties);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                    Internal registry name (ID) for the block.
     * @param blockFactory            Constructor or factory to instantiate the block.
     * @param blockProperties         Unique properties for the block (must not be shared).
     * @param itemPropertiesSupplier  Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, blockFactory, blockProperties, itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockFactory     Constructor or factory to instantiate the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @param itemProperties   Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        return registerWithCustomItem(name, blockFactory, blockProperties, BlockItem::new, itemProperties);
    }

    // Custom BlockItem factory and properties
    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                    Internal registry name (ID) for the block.
     * @param itemFactory             Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemPropertiesSupplier  Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, BlockItemFactory itemFactory, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, BlockBehaviour.Properties::of, itemFactory, itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name            Internal registry name (ID) for the block.
     * @param itemFactory     Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemProperties  Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, BlockItemFactory itemFactory, Item.Properties itemProperties) {
        return registerWithCustomItem(name, BlockBehaviour.Properties::of, itemFactory, itemProperties);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemFactory              Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemPropertiesSupplier   Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, BlockItemFactory itemFactory, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, blockPropertiesSupplier.get(), itemFactory, itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemFactory              Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemProperties           Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, BlockItemFactory itemFactory, Item.Properties itemProperties) {
        return registerWithCustomItem(name, blockPropertiesSupplier.get(), itemFactory, itemProperties);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                    Internal registry name (ID) for the block.
     * @param blockProperties         Unique properties for the block (must not be shared).
     * @param itemFactory             Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemPropertiesSupplier  Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, BlockBehaviour.Properties blockProperties, BlockItemFactory itemFactory, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, Block::new, blockProperties, itemFactory, itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @param itemFactory      Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemProperties   Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public Block registerWithCustomItem(String name, BlockBehaviour.Properties blockProperties, BlockItemFactory itemFactory, Item.Properties itemProperties) {
        return registerWithCustomItem(name, Block::new, blockProperties, itemFactory, itemProperties);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockFactory             Constructor or factory to instantiate the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemFactory              Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemPropertiesSupplier   Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, BlockItemFactory itemFactory, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, blockFactory, blockPropertiesSupplier.get(), itemFactory, itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                     Internal registry name (ID) for the block.
     * @param blockFactory             Constructor or factory to instantiate the block.
     * @param blockPropertiesSupplier  Supplier providing the block's unique (not shared) properties.
     * @param itemFactory              Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemProperties           Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, Supplier<BlockBehaviour.Properties> blockPropertiesSupplier, BlockItemFactory itemFactory, Item.Properties itemProperties) {
        return registerWithCustomItem(name, blockFactory, blockPropertiesSupplier.get(), itemFactory, itemProperties);
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name                    Internal registry name (ID) for the block.
     * @param blockFactory            Constructor or factory to instantiate the block.
     * @param blockProperties         Unique properties for the block (must not be shared).
     * @param itemFactory             Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemPropertiesSupplier  Supplier providing the item's unique (not shared) properties.
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties, BlockItemFactory itemFactory, Supplier<Item.Properties> itemPropertiesSupplier) {
        return registerWithCustomItem(name, blockFactory, blockProperties, itemFactory, itemPropertiesSupplier.get());
    }

    /**
     * Registers a {@link Block} alongside a customized {@link BlockItem} implementation or custom {@link Item.Properties}.
     * @param name             Internal registry name (ID) for the block.
     * @param blockFactory     Constructor or factory to instantiate the block.
     * @param blockProperties  Unique properties for the block (must not be shared).
     * @param itemFactory      Constructor or factory to instantiate a custom {@link BlockItem} extension.
     * @param itemProperties   Unique properties for the item (must not be shared).
     * @return The registered block.
     */
    public <T extends Block> T registerWithCustomItem(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties blockProperties, BlockItemFactory itemFactory, Item.Properties itemProperties) {
        final T block = register(name, blockFactory, blockProperties);
        itemRegistrar.register(name, itemSettings -> itemFactory.apply(block, itemSettings), itemProperties);
        return block;
    }

    /**
     * A functional interface for providing custom {@link BlockItem} implementations during registration.
     */
    @FunctionalInterface
    public interface BlockItemFactory {
        BlockItem apply(Block block, Item.Properties itemProperties);
    }


    /**
     * Registers the flammability stats for a given block.
     * <p>
     *     Note that this is unrelated
     *     to block property {@link BlockBehaviour.Properties#ignitedByLava()}
     *     and item property {@link Item.Properties#fireResistant()}
     * </p>
     *
     * @param block       The block to make flammable.
     * @param burnTime    The time it takes for the block to burn away completely.
     * @param spreadSpeed The speed at which fire spreads to and from this block.
     */
    public static void registerFlammable(Block block, int burnTime, int spreadSpeed) {
        FlammableBlockRegistry.getDefaultInstance().add(block, burnTime, spreadSpeed);
    }
    /**
     * Registers the flammability stats for given blocks.
     * <p>
     *     Note that this is unrelated
     *     to block property {@link BlockBehaviour.Properties#ignitedByLava()}
     *     and item property {@link Item.Properties#fireResistant()}
     * </p>
     *
     * @param burnTime    The time it takes for the blocks to burn away completely.
     * @param spreadSpeed The speed at which fire spreads to and from these blocks.
     * @param blocks      The blocks to make flammable.
     */
    public static void registerFlammable(int burnTime, int spreadSpeed, Block... blocks) {
        for (Block block : blocks) {
            registerFlammable(block, burnTime, spreadSpeed);
        }
    }
}
