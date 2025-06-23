package com.aylanj123.afkcommand.eventhandler;

import com.aylanj123.afkcommand.AFKCommandMod;
import com.aylanj123.afkcommand.Config;
import com.aylanj123.afkcommand.LangKeys;
import com.aylanj123.afkcommand.afkstate.capability.PlayerAFKState;
import com.aylanj123.afkcommand.afkstate.capability.StateSource;
import com.aylanj123.afkcommand.networking.PacketHandler;
import com.aylanj123.afkcommand.networking.packets.IdleConfigS2CPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ServerEventHandler {

    @EventBusSubscriber(modid = AFKCommandMod.MODID)
    public static class ServerForgeEvents {
        @SubscribeEvent
        static void serverSetUp(ServerStartingEvent event) {
            AFKCommandMod.LOGGER.info("Setting up the server");
            Config.serverSidedLoad();
        }

        @SubscribeEvent
        static void playerTicked(PlayerTickEvent.Post event) {
            if(event.getEntity().level().isClientSide()) return;
            PlayerAFKState cap = PlayerAFKState.get(event.getEntity());
            ServerPlayer player = ((ServerPlayer) event.getEntity());
            if (player.hasDisconnected()) return;
            if (cap.isAFK()) cap.addTick();
            if (Config.timeToKick != -1)
                if (cap.timeAFK() > Config.timeToKick)
                    player.connection.disconnect(Component.translatable(LangKeys.KICK_IDLE.key(), Config.timeToKick / 20));
        }

        @SubscribeEvent
        static void entityJoined(EntityJoinLevelEvent event) {
            Entity entity = event.getEntity();
            if (entity instanceof ServerPlayer player) {
                PlayerAFKState cap = PlayerAFKState.get(player);
                if (Config.afkOnLogin) cap.putAFK(StateSource.LOGIN_APPLIED, player);
                PacketHandler.sendPlayer(new IdleConfigS2CPacket(Config.timeToSendAFK), player);
            }
        }

        @SubscribeEvent
        static void playerAttack(LivingDamageEvent.Pre event) {
            if (!Config.invinciblePlayers) return;
            boolean towardsPlayer = event.getEntity() instanceof Player && event.getSource().getEntity() instanceof LivingEntity;
            boolean fromPlayer = event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Enemy || event.getEntity() instanceof NeutralMob || event.getEntity() instanceof Player);
            if (towardsPlayer) {
                ServerPlayer player = (ServerPlayer) event.getEntity();
                PlayerAFKState cap = PlayerAFKState.get(player);
                cap.setLastTimeCombat(player);
            }
            if (fromPlayer) {
                ServerPlayer player = (ServerPlayer) event.getSource().getEntity();
                PlayerAFKState cap = PlayerAFKState.get(player);
                cap.setLastTimeCombat(player);
            }
        }

    }


}