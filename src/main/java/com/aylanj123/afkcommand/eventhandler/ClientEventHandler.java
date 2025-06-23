package com.aylanj123.afkcommand.eventhandler;
import com.aylanj123.afkcommand.AFKCommandMod;
import com.aylanj123.afkcommand.Config;
import com.aylanj123.afkcommand.networking.PacketHandler;
import com.aylanj123.afkcommand.networking.packets.IdledC2SPacket;
import com.aylanj123.afkcommand.networking.stateholder.ClientAFKStateHolder;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class ClientEventHandler {

    @EventBusSubscriber(modid = AFKCommandMod.MODID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        static void clientSetUp(FMLClientSetupEvent event) {
            AFKCommandMod.LOGGER.info("Setting up the client");
            Config.clientSidedLoad();
        }

    }

    @EventBusSubscriber(modid = AFKCommandMod.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        static void clientTick(ClientTickEvent.Post event) {
            if (Minecraft.getInstance().player == null) return;
            if (++ClientAFKStateHolder.currentIdleTime > ClientAFKStateHolder.timeIdle) {
                ClientAFKStateHolder.currentIdleTime = 0;
                PacketHandler.sendServer(new IdledC2SPacket());
            }
        }

    }

}
