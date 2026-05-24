package io.github.renegadeactual.praxis.datagen;

import java.util.concurrent.CompletableFuture;

import io.github.renegadeactual.praxis.Praxis;
import io.github.renegadeactual.praxis.material.Material;
import io.github.renegadeactual.praxis.material.MaterialForm;
import io.github.renegadeactual.praxis.material.Materials;
import io.github.renegadeactual.praxis.registry.PraxisBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;


/**
 * Generates block tag JSON files declaring which tools are needed to mine
 * Praxis blocks, sourced from each material's requiredHarvestTier().
 *
 * Adds every material block to minecraft:mineable/pickaxe, plus the
 * tier-appropriate needs_X_tool tag.
 *
 * Output: src/generated/resources/data/minecraft/tags/block/
 */
public final class PraxisBlockTagsProvider extends BlockTagsProvider {

    public PraxisBlockTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Praxis.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        // machine_casing: pickaxe + stone tier (placeholder, refactor with real tier system later)
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(PraxisBlocks.MACHINE_CASING.get());
        tag(BlockTags.NEEDS_STONE_TOOL).add(PraxisBlocks.MACHINE_CASING.get());

        // Material-driven blocks
        for (Material material : Materials.ALL) {
            if (material.hasForm(MaterialForm.ORE_BLOCK)) {
                Block oreBlock = PraxisBlocks.get(material, MaterialForm.ORE_BLOCK).get();
                tagBlockForTier(oreBlock, material.requiredHarvestTier());
            }
            if (material.hasForm(MaterialForm.BLOCK)) {
                Block storageBlock = PraxisBlocks.get(material, MaterialForm.BLOCK).get();
                tagBlockForTier(storageBlock, material.requiredHarvestTier());
            }
        }
    }

    private void tagBlockForTier(Block block, String tier) {
        // Always pickaxe-mineable
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);

        // Tier-specific tag (the "needs at least X tool" requirement)
        TagKey<Block> tierTag = tierTagFor(tier);
        if (tierTag != null) {
            tag(tierTag).add(block);
        }
    }

    private @Nullable TagKey<Block> tierTagFor(String tier) {
        return switch (tier.toLowerCase()) {
            case "wood" -> null; // Any tool works; no needs_X tag
            case "stone" -> BlockTags.NEEDS_STONE_TOOL;
            case "iron" -> BlockTags.NEEDS_IRON_TOOL;
            case "diamond" -> BlockTags.NEEDS_DIAMOND_TOOL;
            default -> {
                Praxis.LOGGER.warn("Unknown harvest tier '{}' for Praxis block; defaulting to stone tier", tier);
                yield BlockTags.NEEDS_STONE_TOOL;
            }
        };
    }
}