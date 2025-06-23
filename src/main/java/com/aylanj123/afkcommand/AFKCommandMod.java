package com.aylanj123.afkcommand;

import com.aylanj123.afkcommand.registry.AttachmentsRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(AFKCommandMod.MODID)
public class AFKCommandMod
{
    public static final String MODID = "afk_command";

    // slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public AFKCommandMod(IEventBus modEventBus, ModContainer modContainer)
    {
        AttachmentsRegistry.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC, MODID + "-server.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC, MODID + "-client.toml");
    }

}
