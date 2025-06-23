package com.aylanj123.afkcommand.networking;

import com.aylanj123.afkcommand.AFKCommandMod;
import com.aylanj123.afkcommand.networking.packets.GoneAFKS2CPacket;
import com.aylanj123.afkcommand.networking.packets.IdleConfigS2CPacket;
import com.aylanj123.afkcommand.networking.packets.IdledC2SPacket;
import com.aylanj123.afkcommand.networking.packets.MovedC2SPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = AFKCommandMod.MODID)
public class PacketHandler {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.MAIN);

        registrar.playToServer(
                MovedC2SPacket.TYPE,
                MovedC2SPacket.CODEC,
                MovedC2SPacket::handle
        );
        registrar.playToServer(
                IdledC2SPacket.TYPE,
                IdledC2SPacket.CODEC,
                IdledC2SPacket::handle
        );
        registrar.playToClient(
                GoneAFKS2CPacket.TYPE,
                GoneAFKS2CPacket.CODEC,
                GoneAFKS2CPacket::handle
        );
        registrar.playToClient(
                IdleConfigS2CPacket.TYPE,
                IdleConfigS2CPacket.CODEC,
                IdleConfigS2CPacket::handle
        );
    }

    public static void sendServer(CustomPacketPayload msg) {
        PacketDistributor.sendToServer(msg);
    }

    public static void sendPlayer(CustomPacketPayload msg, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, msg);
    }

}
