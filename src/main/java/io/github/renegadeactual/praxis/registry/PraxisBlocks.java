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
 * Standalone blocks are declared as named constants.
 * Material-driven blocks are registered in bulk by iterating
 * {@link Materials#ALL} and are accessed via {@link #get(Material, MaterialForm}.
 */


public final class PraxisBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Praxis.MODID);

    // ----- Standalone structural blocks -----

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

    // ----- Material-Driven blocks -----

    private static final Map<MaterialFormKey, DeferredBlock<Block>> MATERIAL_BLOCKS = new HashMap<>();

    static {
        for (Material material : Materials.ALL) {
            if (material.hasForm(MaterialForm.ORE_BLOCK)) {
                registerOreBlock(material);
            }
            if (material.hasForm(MaterialForm.BLOCK)) {
                registerStorageBlock(material);
            }
        }
    }

    private static void registerOreBlock(Material material) {
        String name = material.id() + "_ore";
        DeferredBlock<Block> block = BLOCKS.register(
                name,
                registryName -> new Block(
                        BlockBehaviour.Properties.of()
                                .mapColor(MapColor.STONE)
                                .strength(3.0f, 3.0f)
                                .sound(SoundType.STONE)
                                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                )
        );
        MATERIAL_BLOCKS.put(new MaterialFormKey(material, MaterialForm.ORE_BLOCK), block);
    }

    private static void registerStorageBlock(Material material) {
        String name = material.id() + "_block";
        DeferredBlock<Block> block = BLOCKS.register(
                name,
                registryName -> new Block(
                        BlockBehaviour.Properties.of()
                                .mapColor(MapColor.METAL)
                                .strength(5.0f, 6.0f)
                                .sound(SoundType.METAL)
                                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                )
        );
        MATERIAL_BLOCKS.put(new MaterialFormKey(material, MaterialForm.BLOCK), block);
    }

    /**
     * Look up the registered block for a given (material, form). Returns null if not registered
     */
    public static DeferredBlock<Block> get(Material material, MaterialForm form) {
        return MATERIAL_BLOCKS.get(new MaterialFormKey(material, form));
    }

    private static Map<Block, MaterialFormKey> blockToMaterialForm = null;

    private static Map<Block, MaterialFormKey> getBlockToMaterialForm() {
        if (blockToMaterialForm == null) {
            Map<Block, MaterialFormKey> map = new HashMap<>();
            for (Map.Entry<MaterialFormKey, DeferredBlock<Block>> entry : MATERIAL_BLOCKS.entrySet()) {
                map.put(entry.getValue().get(), entry.getKey());
            }
            blockToMaterialForm = map;
        }
        return blockToMaterialForm;
    }

    /**
     * Reverse lookup: given a Block, return its (material, form) if it's a material-driven
     * Praxis block. Returns null for standalone blocks like MACHINE_CASING and for non-Praxis blocks.
     */
    public static MaterialFormKey materialFormFor(Block block) {
        return getBlockToMaterialForm().get(block);
    }

    private PraxisBlocks() {}

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
