package io.github.renegadeactual.praxis.event;

import io.github.renegadeactual.praxis.Praxis;
import io.github.renegadeactual.praxis.material.Material;
import io.github.renegadeactual.praxis.material.MaterialFormKey;
import io.github.renegadeactual.praxis.registry.PraxisItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

/**
 * Adds Praxis-specific tooltip lines (chemical formula, elements, required tool tier)
 * to material-driven items.
 *
 * Fires on the game event bus for every tooltip render. Skips items that aren't
 * registered as material-driven Praxis items (vanilla items, machine_casing, items
 * from other mods).
 */
@EventBusSubscriber(modid = Praxis.MODID)
public final class MaterialTooltipHandler {

    private MaterialTooltipHandler() {}

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        Item item = event.getItemStack().getItem();
        MaterialFormKey key = PraxisItems.materialFormFor(item);
        if (key == null) {
            return;
        }

        Material material = key.material();

        if (!material.formula().isEmpty()) {
            event.getToolTip().add(Component.literal(material.formula()).withStyle(ChatFormatting.GRAY));
        }

        if (!material.elementsContained().isEmpty()) {
            String elementsList = String.join(", ", material.elementsContained());
            event.getToolTip().add(
                    Component.literal("Elements: " + elementsList).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)
            );
        }

        String tier = material.requiredHarvestTier();
        if (tier != null && !tier.isEmpty()) {
            String prettyTier = Character.toUpperCase(tier.charAt(0)) + tier.substring(1);
            event.getToolTip().add(
                    Component.literal("Requires: " + prettyTier + " Pickaxe").withStyle(ChatFormatting.YELLOW)
            );
        }
    }
}