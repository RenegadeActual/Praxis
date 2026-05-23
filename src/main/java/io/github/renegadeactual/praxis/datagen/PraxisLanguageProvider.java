package io.github.renegadeactual.praxis.datagen;

import io.github.renegadeactual.praxis.Praxis;
import io.github.renegadeactual.praxis.material.Material;
import io.github.renegadeactual.praxis.material.MaterialForm;
import io.github.renegadeactual.praxis.material.Materials;
import io.github.renegadeactual.praxis.registry.PraxisBlocks;
import io.github.renegadeactual.praxis.registry.PraxisItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

/**
 * Generates the English language file for Praxis.
 *
 * Translation keys are computed from material data, so adding a new material
 * in Materials.java produces all of its translation entries automatically.
 *
 * Output: src/generated/resources/assets/praxis/lang/en_us.json
 */
public final class PraxisLanguageProvider extends LanguageProvider {

    public PraxisLanguageProvider(PackOutput output) {
        super(output, Praxis.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // ----- Standalone blocks/items -----
        addBlock(PraxisBlocks.MACHINE_CASING, "Machine Casing");

        // ----- Creative tab -----
        add("itemGroup.praxis.main", "Praxis");

        // ----- Material-driven items, generated from Materials.ALL -----
        for (Material material : Materials.ALL) {
            for (MaterialForm form : material.availableForms()) {
                addMaterialForm(material, form);
            }
        }
    }

    private void addMaterialForm(Material material, MaterialForm form) {
        String name = material.displayName();
        switch (form) {
            case ORE_BLOCK -> {
                add("block.praxis." + material.id() + "_ore", name + " Ore");
                add("item.praxis." + material.id() + "_ore", name + " Ore");
            }
            case BLOCK -> {
                add("block.praxis." + material.id() + "_block", "Block of " + name);
                add("item.praxis." + material.id() + "_block", "Block of " + name);
            }
            case RAW_ORE -> add("item.praxis.raw_" + material.id(), "Raw " + name);
            case CRUSHED_ORE -> add("item.praxis.crushed_" + material.id(), "Crushed " + name);
            case DUST -> add("item.praxis." + material.id() + "_dust", name + " Dust");
            case INGOT -> add("item.praxis." + material.id() + "_ingot", name + " Ingot");
            case NUGGET -> add("item.praxis." + material.id() + "_nugget", name + " Nugget");
            case PLATE -> add("item.praxis." + material.id() + "_plate", name + " Plate");
        }
    }
}