package io.github.renegadeactual.praxis.compat.jade;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import net.minecraft.world.level.block.Block;

/**
 * Jade compatibility plugin for Praxis.
 *
 * Registered automatically via the @WailaPlugin annotation — Jade scans the classpath
 * at startup, finds this class, and calls register() and registerClient().
 *
 * Since Praxis doesn't currently have any server-side state to sync (everything is
 * static material data), only the client registration does anything meaningful.
 * When we add multiblocks with internal state (bloomery progress, etc), this is
 * where the server data provider will be registered.
 */
@WailaPlugin
public final class PraxisJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        // No server-side data providers yet. Multiblock state goes here later.
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(MaterialBlockComponentProvider.INSTANCE, Block.class);
    }
}