package com.aylanj123.afkcommand.afkstate.capability;

import com.aylanj123.afkcommand.registry.AttachmentsRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerAFKStateProvider implements ICapabilityProvider<Player, Void, PlayerAFKState> {

    private final Map<Entity, WeakReference<PlayerAFKState>> cache = new ConcurrentHashMap<>();

    @Override
    public @Nullable PlayerAFKState getCapability(@NotNull Player player, Void unused) {
        var cached_weak = cache.get(player);
        if (cached_weak != null) {
            var cached = cached_weak.get();
            if (cached != null) {
                return cached;
            }
        }
        var fetched = player.getData(AttachmentsRegistry.AFK_DATA);
        cache.put(player, new WeakReference<>(fetched));
        return fetched;
    }
}
