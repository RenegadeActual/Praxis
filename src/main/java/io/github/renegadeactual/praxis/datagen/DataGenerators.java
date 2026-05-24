package io.github.renegadeactual.praxis.datagen;

import io.github.renegadeactual.praxis.Praxis;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * Entry point for Praxis data generation.
 *
 * This class listens for the {@link GatherDataEvent.Client} event, which fires
 * during a {@code runClientData} (or older {@code runData}) Gradle task. We
 * register all data providers here. Each provider generates one category of
 * JSON files (models, blockstates, loot tables, tags, language entries, etc.)
 * under {@code src/generated/resources/}.
 *
 * The annotation registers this class as a subscriber on the mod event bus for
 * the Praxis mod. The handler is static; we don't need an instance of this class.
 */
@EventBusSubscriber(modid = Praxis.MODID)
public final class DataGenerators {

    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        Praxis.LOGGER.info("Praxis datagen: GatherDataEvent.Client fired. No providers registered yet.");

        // Providers will be registered here:
        event.createProvider(PraxisLanguageProvider::new);
        // event.createProvider(PraxisModelProvider.Runner::new);
        event.createProvider(PraxisLootTableProvider::new);
        event.createProvider(PraxisBlockTagsProvider::new);
        // event.createProvider(PraxisItemTagProvider::new);
    }
}