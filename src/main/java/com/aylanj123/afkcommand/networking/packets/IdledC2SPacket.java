package com.aylanj123.afkcommand.networking.packets;

import com.aylanj123.afkcommand.AFKCommandMod;
import com.aylanj123.afkcommand.afkstate.capability.PlayerAFKState;
import com.aylanj123.afkcommand.afkstate.capability.StateSource;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class IdledC2SPacket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<IdledC2SPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AFKCommandMod.MODID, "idled_c2s_packet"));

    public static final StreamCodec<ByteBuf, IdledC2SPacket> CODEC = StreamCodec.unit(new IdledC2SPacket());

    public IdledC2SPacket() {}

    public void handle(IPayloadContext cx) {
        ServerPlayer player = (ServerPlayer) cx.player();
        if (player == null) {
            return;
        }
        PlayerAFKState cap = PlayerAFKState.get(player);
        if (!cap.isAFK()) cap.putAFK(StateSource.IDLED_TOO_LONG, player);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(IdledC2SPacket.class);
    }
}
