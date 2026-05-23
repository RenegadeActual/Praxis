package io.github.renegadeactual.praxis.registry;

import io.github.renegadeactual.praxis.Praxis;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Central registry for all Praxis blocks.
 *
 * Blocks are registered as DeferredBlock instances - handles that get
 * populated by NeoForge during the registration phase of mod loading.
 * Never construct blocks directly - Use DeferredRegister.
 */


public final class PraxisBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Praxis.MODID);
    public static final DeferredBlock<Block> MACHINE_CASING = BLOCKS.register(
            "machine_casing",
            registryName -> new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0f, 6.0f)
                            .sound(SoundType.METAL)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    private PraxisBlocks() {}

    /**
     * Called from the main mod class to attach this register to the mod event bus.
     * Without this call, none of the blocks get registered. I fucking hate this shit.
     */
    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
