package io.github.renegadeactual.praxis.compat.jade;

import io.github.renegadeactual.praxis.Praxis;
import io.github.renegadeactual.praxis.material.Material;
import io.github.renegadeactual.praxis.material.MaterialFormKey;
import io.github.renegadeactual.praxis.registry.PraxisBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

/**
 * Adds Praxis material data (chemical formula, elements, harvest tier) to the Jade
 * overlay when looking at a material-driven Praxis block.
 *
 * Mirrors the in-inventory tooltip lines added by MaterialTooltipHandler — players
 * see the same info whether they're hovering an item or looking at a placed block.
 *
 * Returns early for blocks that aren't material-driven, so vanilla blocks and
 * standalone Praxis blocks like machine_casing get default Jade behavior.
 */
public final class MaterialBlockComponentProvider implements IBlockComponentProvider {

    public static final MaterialBlockComponentProvider INSTANCE = new MaterialBlockComponentProvider();

    private static final Identifier UID =
            Identifier.fromNamespaceAndPath(Praxis.MODID, "material_info");

    private MaterialBlockComponentProvider() {}

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        MaterialFormKey key = PraxisBlocks.materialFormFor(accessor.getBlock());
        if (key == null) {
            return;
        }

        Material material = key.material();

        if (!material.formula().isEmpty()) {
            tooltip.add(Component.literal(material.formula()).withStyle(ChatFormatting.GRAY));
        }

        if (!material.elementsContained().isEmpty()) {
            String elementsList = String.join(", ", material.elementsContained());
            tooltip.add(Component.literal("Elements: " + elementsList)
                    .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        }

        String tier = material.requiredHarvestTier();
        if (tier != null && !tier.isEmpty()) {
            String prettyTier = Character.toUpperCase(tier.charAt(0)) + tier.substring(1);
            tooltip.add(Component.literal("Requires: " + prettyTier + " Pickaxe")
                    .withStyle(ChatFormatting.YELLOW));
        }
    }

    @Override
    public Identifier getUid() {
        return UID;
    }
}