package com.badbones69.crazyenchantments.paper.listeners;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.Starter;
import com.badbones69.crazyenchantments.paper.api.enums.CEnchantments;
import com.badbones69.crazyenchantments.paper.api.events.AuraActiveEvent;
import com.badbones69.crazyenchantments.paper.api.objects.CEnchantment;
import com.badbones69.crazyenchantments.paper.controllers.settings.EnchantmentBookSettings;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuraListener implements Listener {

    @NotNull
    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    @NotNull
    private final Starter starter = this.plugin.getStarter();

    @NotNull
    private final EnchantmentBookSettings enchantmentBookSettings = this.starter.getEnchantmentBookSettings();

    private final CEnchantments[] AURA_ENCHANTMENTS = {
            CEnchantments.BLIZZARD,
            CEnchantments.ACIDRAIN,
            CEnchantments.SANDSTORM,
            CEnchantments.RADIANT,
            CEnchantments.INTIMIDATE
    };

    private static final int NEARBY_PLAYER_RADIUS = 3;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() == to.getBlockX()
        && from.getBlockY() == to.getBlockY()
        && from.getBlockZ() == to.getBlockZ()) return;

        List<Player> players = getNearbyPlayers(player);

        if (players.isEmpty()) return;

        applyAuraEnchantments(player, players);

        for (Player other : players) {
            applyAuraEnchantments(other, player);
        }
    }

    private void applyAuraEnchantments(Player source, Player target) {
        EntityEquipment equipment = source.getEquipment();
        if (equipment == null) return;

        for (ItemStack item : equipment.getArmorContents()) {
            if (item == null) continue;
            Map<CEnchantment, Integer> itemEnchantments = this.enchantmentBookSettings.getEnchantments(item);
            itemEnchantments.forEach((enchantment, level) -> {
                CEnchantments enchantmentEnum = getAuraEnchantmentEnum(enchantment);
                if (enchantmentEnum != null) {
                    this.plugin.getServer().getPluginManager().callEvent(new AuraActiveEvent(source, target, enchantmentEnum, level));
                }
            });
        }
    }

    @Nullable
    private CEnchantments getAuraEnchantmentEnum(CEnchantment enchantment) {
        return Arrays.stream(AURA_ENCHANTMENTS)
                .filter(enchantmentEnum -> enchantmentEnum.getName().equals(enchantment.getName()))
                .findFirst()
                .orElse(null);
    }

    private List<Player> getNearbyPlayers(Player player) {
        return player.getNearbyEntities(NEARBY_PLAYER_RADIUS, NEARBY_PLAYER_RADIUS, NEARBY_PLAYER_RADIUS)
                .stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .filter(otherPlayer -> !otherPlayer.getUniqueId().equals(player.getUniqueId()))
                .collect(Collectors.toList());
    }
}
