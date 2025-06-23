package com.aylanj123.afkcommand.networking.packets;


import com.aylanj123.afkcommand.AFKCommandMod;
import com.aylanj123.afkcommand.networking.stateholder.ClientAFKStateHolder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class IdleConfigS2CPacket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<IdleConfigS2CPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AFKCommandMod.MODID, "idle_config_s2c_packet"));

    public static final StreamCodec<ByteBuf, IdleConfigS2CPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            (x) -> x.time,
            IdleConfigS2CPacket::new
    );

    int time;

    public IdleConfigS2CPacket(int time) {
        this.time = time;
    }

    public void handle(IPayloadContext cx) {
        ClientAFKStateHolder.timeIdle = time;
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
