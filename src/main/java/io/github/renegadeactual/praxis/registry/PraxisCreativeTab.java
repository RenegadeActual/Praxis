package io.github.renegadeactual.praxis.registry;

import io.github.renegadeactual.praxis.Praxis;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers the creative-mode tab(s) for Praxis content.
 *
 * The main tab automatically collects every BlockItem and Item registered
 * through PraxisItems, in registration order. To add new content to the tab,
 * register it in PraxisItems - no changes required here.
 */
public class PraxisCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Praxis.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB =
            CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.praxis.main")) // the tab's display name routed through a translation key
                    .icon(() -> PraxisItems.MACHINE_CASING.get().getDefaultInstance()) // the item that shows on the tab
                    .displayItems((parameters, output) -> {
                        output.accept(PraxisItems.MACHINE_CASING.get());
                    })
                    .build()); // always at the end of the builder methods

    private PraxisCreativeTab() {}

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
