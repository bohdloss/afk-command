package com.aylanj123.afkcommand.afkstate.capability;

import com.aylanj123.afkcommand.AFKCommandMod;
import com.aylanj123.afkcommand.Config;
import com.aylanj123.afkcommand.LangKeys;
import com.aylanj123.afkcommand.networking.PacketHandler;
import com.aylanj123.afkcommand.networking.packets.GoneAFKS2CPacket;
import com.aylanj123.afkcommand.registry.CapabilitiesRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlayerAFKState implements INBTSerializable<CompoundTag> {

    public static @NotNull PlayerAFKState get(@NotNull Player player) {
        return Objects.requireNonNull(player.getCapability(CapabilitiesRegistry.AFK_STATE));
    }

    private boolean afk;
    private StateSource source;
    private int timeAFK;
    private long lastTimeAFK;
    private long lastTimeCombat;

    public boolean isAFK() {
        return afk;
    }

    public StateSource getSource() {
        return source;
    }

    public int timeAFK() {
        return timeAFK;
    }

    public void addTick() {
        timeAFK++;
    }

    public long getLastTimeAFK() {
        return lastTimeAFK;
    }
    public long getLastTimeCombat() {
        return lastTimeCombat;
    }

    public void setLastTimeCombat(ServerPlayer player) {
        this.lastTimeCombat = player.serverLevel().getGameTime();
    }

    public void putAFK(StateSource source, ServerPlayer player) {
        this.source = source;
        afk = true;
        timeAFK = 0;
        PacketHandler.sendPlayer(new GoneAFKS2CPacket(), player);
        player.displayClientMessage(Component.translatable(LangKeys.COMMAND_ANSWER_ENTER.key()), true);
        updateSleep(player);
        if (Config.invinciblePlayers) player.setInvulnerable(true);
    }

    public void removeAFK(ServerPlayer player) {
        AFKCommandMod.LOGGER.info(String.format(
            "%s has returned from being AFK after %o ticks (%fs) and had gone AFK %s",
            player.getName().getString(), timeAFK, timeAFK / 20F,
            source == StateSource.SELF_APPLY ? "on their own" :
            source == StateSource.OPERATOR_APPLIED ? "because of an operator" :
            source == StateSource.LOGIN_APPLIED ? "on login" : " by idling for too long"
        ));
        afk = false;
        timeAFK = -1;
        source = null;
        lastTimeAFK = player.serverLevel().getGameTime();
        player.displayClientMessage(Component.translatable(LangKeys.COMMAND_ANSWER_EXIT.key()), true);
        updateSleep(player);
        if (Config.invinciblePlayers) player.setInvulnerable(false);
    }

    public void copyFrom(PlayerAFKState oldState) {
        afk = oldState.afk;
        source = oldState.source;
        timeAFK = oldState.timeAFK;
        lastTimeCombat = oldState.lastTimeCombat;
    }

    private void updateSleep(ServerPlayer player) {
        player.serverLevel().updateSleepingPlayerList();
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        var tag = new CompoundTag();
        tag.putBoolean("afk", afk);
        tag.putString("source", source == null ? "" : source.intoString());
        tag.putInt("time_afk", timeAFK);
        tag.putLong("last_time_afk", lastTimeAFK);
        tag.putLong("last_time_combat", lastTimeCombat);
        return null;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
        afk = tag.getBoolean("afk");
        source = StateSource.fromString(tag.getString("source"));
        timeAFK = tag.getInt("time_afk");
        lastTimeAFK = tag.getLong("last_time_afk");
        lastTimeCombat = tag.getLong("last_time_combat");
    }
}
