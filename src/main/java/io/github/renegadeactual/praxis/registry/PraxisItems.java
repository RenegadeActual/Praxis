package io.github.renegadeactual.praxis.registry;

import io.github.renegadeactual.praxis.Praxis;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Central Registry for all Praxis Items
 *
 * Includes BlockItems (inventory items) and standalone items (raw materials, dusts, ingots, etc)
 */
public class PraxisItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Praxis.MODID);
    public static final DeferredItem<BlockItem> MACHINE_CASING =
            ITEMS.registerSimpleBlockItem(PraxisBlocks.MACHINE_CASING);

    private PraxisItems() {}

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
