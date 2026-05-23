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
package io.github.renegadeactual.praxis.material;

import java.util.List;

/**
 * Static registry of all Praxis materials.
 *
 * Materials are defined here as public static final constants. Each declaration
 * is a complete description of a substance — its identity, appearance, chemistry,
 * and which physical forms it exists in.
 *
 * To add a new material: add a new constant here, then also add it to {@link #ALL}.
 * The rest of the mod (PraxisItems, PraxisBlocks, tooltip system, recipes) reads
 * from this list and adapts automatically.
 *
 * In a future version, this will be backed by a data-driven loader reading from
 * JSON. For now, hardcoded is simpler and lets us iterate on the data shape.
 */
public final class Materials {

    // ============================================================
    // METALS
    // ============================================================

    public static final Material COPPER = Material.builder("copper")
            .displayName("Copper")
            .formula("Cu")
            .elementsContained("Cu")
            .color(0xB87333)
            .hardnessMohs(3.0f)
            .density(8.96f)
            .requiredHarvestTier("stone")
            .geologicalContext("Soft, reddish metal. Found native in oxidized zones of copper deposits.")
            .forms(MaterialForm.DUST, MaterialForm.INGOT, MaterialForm.NUGGET, MaterialForm.PLATE, MaterialForm.BLOCK)
            .build();

    public static final Material IRON = Material.builder("iron")
            .displayName("Iron")
            .formula("Fe")
            .elementsContained("Fe")
            .color(0xD8D8D8)
            .hardnessMohs(4.0f)
            .density(7.87f)
            .requiredHarvestTier("stone")
            .geologicalContext("Most-used industrial metal. Reduced from iron oxide and carbonate ores via smelting.")
            .forms(MaterialForm.DUST, MaterialForm.INGOT, MaterialForm.NUGGET, MaterialForm.PLATE, MaterialForm.BLOCK)
            .build();

    // ============================================================
    // ORES — copper-bearing
    // ============================================================

    public static final Material MALACHITE = Material.builder("malachite")
            .displayName("Malachite")
            .formula("Cu₂CO₃(OH)₂")
            .elementsContained("Cu", "C", "O", "H")
            .color(0x4D8E5F)
            .hardnessMohs(3.5f)
            .density(4.0f)
            .requiredHarvestTier("stone")
            .geologicalContext("Secondary mineral in the oxidized zones of copper deposits. " +
                    "Decomposes on heating to copper oxide, releasing CO₂ and water.")
            .forms(MaterialForm.ORE_BLOCK, MaterialForm.RAW_ORE, MaterialForm.DUST)
            .build();

    // ============================================================
    // The master list. Order here determines order in creative tabs
    // and JEI. Group materials thematically — metals first, then ores
    // grouped by primary metal, then non-metals, etc.
    // ============================================================

    public static final List<Material> ALL = List.of(
            COPPER,
            IRON,
            MALACHITE
    );

    private Materials() {}
}