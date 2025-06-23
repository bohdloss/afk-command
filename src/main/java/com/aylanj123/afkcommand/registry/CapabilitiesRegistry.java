package com.aylanj123.afkcommand.registry;

import com.aylanj123.afkcommand.AFKCommandMod;
import com.aylanj123.afkcommand.afkstate.capability.PlayerAFKState;
import com.aylanj123.afkcommand.afkstate.capability.PlayerAFKStateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CapabilitiesRegistry {

    public static EntityCapability<PlayerAFKState, Void> AFK_STATE = EntityCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(AFKCommandMod.MODID, "afk_handler"),
            PlayerAFKState.class
    );

    public static void register(RegisterCapabilitiesEvent event) {
        event.registerEntity(
                CapabilitiesRegistry.AFK_STATE,
                EntityType.PLAYER,
                new PlayerAFKStateProvider()

        );
    }

}
