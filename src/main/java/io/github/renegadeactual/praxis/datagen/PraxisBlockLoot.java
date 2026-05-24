package io.github.renegadeactual.praxis.datagen;

import java.util.HashSet;
import java.util.Set;

import io.github.renegadeactual.praxis.material.Material;
import io.github.renegadeactual.praxis.material.MaterialForm;
import io.github.renegadeactual.praxis.material.Materials;
import io.github.renegadeactual.praxis.registry.PraxisBlocks;
import io.github.renegadeactual.praxis.registry.PraxisItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Generates block loot tables for Praxis.
 *
 * Storage blocks and the standalone machine_casing drop themselves.
 * Ore blocks drop the corresponding raw_ore item (not the block).
 *
 * Output: src/generated/resources/data/praxis/loot_table/blocks/
 */
public final class PraxisBlockLoot extends BlockLootSubProvider {

    public PraxisBlockLoot(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    @Override
    protected void generate() {
        // Standalone block — drops itself
        dropSelf(PraxisBlocks.MACHINE_CASING.get());

        // Material-driven blocks
        for (Material material : Materials.ALL) {
            if (material.hasForm(MaterialForm.ORE_BLOCK)) {
                Block oreBlock = PraxisBlocks.get(material, MaterialForm.ORE_BLOCK).get();
                Item rawDrop = PraxisItems.get(material, MaterialForm.RAW_ORE).get();
                add(oreBlock, createOreDrop(oreBlock, rawDrop));
            }
            if (material.hasForm(MaterialForm.BLOCK)) {
                Block storageBlock = PraxisBlocks.get(material, MaterialForm.BLOCK).get();
                dropSelf(storageBlock);
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        Set<Block> blocks = new HashSet<>();
        blocks.add(PraxisBlocks.MACHINE_CASING.get());

        for (Material material : Materials.ALL) {
            if (material.hasForm(MaterialForm.ORE_BLOCK)) {
                blocks.add(PraxisBlocks.get(material, MaterialForm.ORE_BLOCK).get());
            }
            if (material.hasForm(MaterialForm.BLOCK)) {
                blocks.add(PraxisBlocks.get(material, MaterialForm.BLOCK).get());
            }
        }

        return blocks;
    }
}