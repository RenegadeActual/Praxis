/**
 * MIT License
 *
 * Copyright (c) 2026 William Whatley
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.renegadeactual.praxis.registry;

import java.util.HashMap;
import java.util.Map;

import io.github.renegadeactual.praxis.Praxis;
import io.github.renegadeactual.praxis.material.Material;
import io.github.renegadeactual.praxis.material.MaterialForm;
import io.github.renegadeactual.praxis.material.MaterialFormKey;
import io.github.renegadeactual.praxis.material.Materials;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.level.block.Block;

/**
 * Central registry for all Praxis items.
 *
 * Standalone items (tools, machine parts, components) are declared as named constants.
 * Material-driven items (one per material × form combination) are registered in bulk
 * by iterating {@link Materials#ALL} and accessed via {@link #get(Material, MaterialForm)}.
 */
public final class PraxisItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Praxis.MODID);

    // ----- Standalone items -----

    public static final DeferredItem<BlockItem> MACHINE_CASING =
            ITEMS.registerSimpleBlockItem(PraxisBlocks.MACHINE_CASING);

    // ----- Material-driven items -----

    private static final Map<MaterialFormKey, DeferredItem<? extends Item>> MATERIAL_ITEMS = new HashMap<>();

    static {
        for (Material material : Materials.ALL) {
            for (MaterialForm form : material.availableForms()) {
                registerMaterialForm(material, form);
            }
        }
    }

    private static void registerMaterialForm(Material material, MaterialForm form) {
        DeferredItem<? extends Item> item = switch (form) {
            case ORE_BLOCK -> registerBlockItem(material, MaterialForm.ORE_BLOCK, material.id() + "_ore");
            case BLOCK -> registerBlockItem(material, MaterialForm.BLOCK, material.id() + "_block");
            case RAW_ORE -> registerSimpleItem("raw_" + material.id());
            case DUST -> registerSimpleItem(material.id() + "_dust");
            case CRUSHED_ORE -> registerSimpleItem("crushed_" + material.id());
            case INGOT -> registerSimpleItem(material.id() + "_ingot");
            case NUGGET -> registerSimpleItem(material.id() + "_nugget");
            case PLATE -> registerSimpleItem(material.id() + "_plate");
        };

        MATERIAL_ITEMS.put(new MaterialFormKey(material, form), item);
    }

    private static DeferredItem<BlockItem> registerBlockItem(Material material, MaterialForm form, String name) {
        DeferredBlock<Block> block = PraxisBlocks.get(material, form);
        if (block == null) {
            throw new IllegalStateException(
                    "Material '" + material.id() + "' declares form " + form +
                            " but no block was registered in PraxisBlocks. " +
                            "Ensure PraxisBlocks handles this form."
            );
        }
        return ITEMS.registerSimpleBlockItem(block);
    }

    private static DeferredItem<Item> registerSimpleItem(String name) {
        return ITEMS.registerSimpleItem(name);
    }

    /**
     * Look up the registered item for a given (material, form). Returns null if not registered.
     */
    public static DeferredItem<? extends Item> get(Material material, MaterialForm form) {
        return MATERIAL_ITEMS.get(new MaterialFormKey(material, form));
    }

    private PraxisItems() {}

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}