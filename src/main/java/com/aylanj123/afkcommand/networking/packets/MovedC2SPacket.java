package com.aylanj123.afkcommand.networking.packets;

import com.aylanj123.afkcommand.AFKCommandMod;
import com.aylanj123.afkcommand.afkstate.capability.PlayerAFKState;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class MovedC2SPacket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MovedC2SPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AFKCommandMod.MODID, "moved_c2s_packet"));

    public static final StreamCodec<ByteBuf, MovedC2SPacket> CODEC = StreamCodec.unit(new MovedC2SPacket());

    public MovedC2SPacket() {}

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext cx) {
        ServerPlayer player = (ServerPlayer) cx.player();
        if (player == null) {
            return;
        }
        PlayerAFKState cap = PlayerAFKState.get(player);
        cap.removeAFK(player);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(MovedC2SPacket.class);
    }

}
