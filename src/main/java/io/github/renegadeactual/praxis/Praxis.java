package io.github.renegadeactual.praxis;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import io.github.renegadeactual.praxis.registry.PraxisBlocks;
import io.github.renegadeactual.praxis.registry.PraxisItems;
import io.github.renegadeactual.praxis.registry.PraxisCreativeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Praxis.MODID)
public class Praxis {
    public static final String MODID = "praxis";
    public static final Logger LOGGER = LogUtils.getLogger();
    public Praxis(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        PraxisBlocks.register(modEventBus);
        PraxisItems.register(modEventBus);
        PraxisCreativeTab.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Praxis common setup complete");
    }

    @SubscribeEvent
    public void onSeverStarting(ServerStartingEvent event) {
        LOGGER.info("Praxis: server starting");
    }
}