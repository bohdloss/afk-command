package com.aylanj123.afkcommand.networking.packets;


import com.aylanj123.afkcommand.AFKCommandMod;
import com.aylanj123.afkcommand.networking.stateholder.ClientAFKStateHolder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class GoneAFKS2CPacket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<GoneAFKS2CPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AFKCommandMod.MODID, "gone_s2c_packet"));

    public static final StreamCodec<ByteBuf, GoneAFKS2CPacket> CODEC = StreamCodec.unit(new GoneAFKS2CPacket());

    public GoneAFKS2CPacket() {}

    public void handle(IPayloadContext cx) {
        ClientAFKStateHolder.afk = true;
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(GoneAFKS2CPacket.class);
    }
}
