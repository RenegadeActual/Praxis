package io.github.renegadeactual.praxis.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

/**
 * Top-level loot table provider. Wraps PraxisBlockLoot (and, eventually,
 * any other category sub-providers for mob drops, dungeon chests, etc.).
 */
public final class PraxisLootTableProvider extends LootTableProvider {

    public PraxisLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, java.util.Set.of(), List.of(
                new SubProviderEntry(PraxisBlockLoot::new, LootContextParamSets.BLOCK)
        ), lookupProvider);
    }
}