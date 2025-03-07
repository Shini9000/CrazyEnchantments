package com.badbones69.crazyenchantments.paper.api.utils;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.Starter;
import com.badbones69.crazyenchantments.paper.api.enums.CEnchantments;
import com.badbones69.crazyenchantments.paper.api.objects.CEnchantment;
import com.badbones69.crazyenchantments.paper.api.objects.EnchantedArrow;
import com.badbones69.crazyenchantments.paper.api.objects.EnchantedTrident; // NEW
import com.badbones69.crazyenchantments.paper.controllers.settings.EnchantmentBookSettings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Trident;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TridentUtils {

    @NotNull
    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    @NotNull
    private final Starter starter = this.plugin.getStarter();

    @NotNull
    private final EnchantmentBookSettings enchantmentBookSettings = this.starter.getEnchantmentBookSettings();

    // Sticky Shot
    private final List<Block> waterBlocks = new ArrayList<>();

    private final List<EnchantedTrident> enchantedTridents = new ArrayList<>();

    public void addTrident(Trident thrownTrident, ItemStack heldTrident, Map<CEnchantment, Integer> enchantments) {
        if (thrownTrident == null) return;

        EnchantedTrident enchantedTrident = new EnchantedTrident(thrownTrident, heldTrident, enchantments);

        this.enchantedTridents.add(enchantedTrident);
    }

    public void removeTrident(EnchantedTrident enchantedTrident) {
        if (!this.enchantedTridents.contains(enchantedTrident) || enchantedTrident == null) return;

        this.enchantedTridents.remove(enchantedTrident);
    }

    public boolean isTridentEnchantActive(CEnchantments customEnchant, EnchantedTrident enchantedTrident) {
        return customEnchant.isActivated() &&
                enchantedTrident.hasEnchantment(customEnchant) &&
                customEnchant.chanceSuccessful(enchantedTrident.getLevel(customEnchant));
    }

    public boolean allowsCombat(Entity entity) {
        return this.starter.getPluginSupport().allowCombat(entity.getLocation());
    }

    public EnchantedTrident getEnchantedTrident(Trident thrownTrident) {
        return this.enchantedTridents.stream().filter((enchThrownTrident) -> enchThrownTrident != null && enchThrownTrident.thrownTrident() != null && enchThrownTrident.thrownTrident().equals(thrownTrident)).findFirst().orElse(null);
    }
    /*
    // Multi Arrow Start!

    public void spawnArrows(LivingEntity shooter, Entity projectile, ItemStack bow) {
        Arrow spawnedArrow = shooter.getWorld().spawn(projectile.getLocation(), Arrow.class);

        EnchantedArrow enchantedMultiArrow = new EnchantedArrow(spawnedArrow, bow, enchantmentBookSettings.getEnchantments(bow));

        this.enchantedArrows.add(enchantedMultiArrow);

        spawnedArrow.setShooter(shooter);

        Vector vector = new Vector(randomSpread(), 0, randomSpread());

        spawnedArrow.setVelocity(projectile.getVelocity().add(vector));

        if (((Arrow) projectile).isCritical()) spawnedArrow.setCritical(true);

        if (projectile.getFireTicks() > 0) spawnedArrow.setFireTicks(projectile.getFireTicks());

        spawnedArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
    }

    private float randomSpread() {
        float spread = (float) .2;
        return -spread + (float) (Math.random() * (spread * 2));
    }
    // Multi Arrow End!
    */


    /*
    // Sticky Shot Start!
    // WaterBender Start! // IN PROGRESS
    public List<Block> getWaterBlocks() {
        return this.waterBlocks;
    }

    public void spawnWaterBlocks(Entity hitEntity, EnchantedTrident enchantedTrident) {
        if (enchantedTrident == null) return;

        Trident thrownTrident = enchantedTrident.getTrident();

        if (!(EnchantUtils.isEventActive(CEnchantments.STICKYSHOT, enchantedArrow.getShooter(), enchantedArrow.arrow().getWeapon(), enchantedArrow.getEnchantments()))) return;

        if (hitEntity == null) {
            Location entityLocation = arrow.getLocation();

            if (entityLocation.getBlock().getType() != Material.AIR) return;

            entityLocation.getBlock().setType(Material.COBWEB);
            this.webBlocks.add(entityLocation.getBlock());

            this.plugin.getServer().getRegionScheduler().runDelayed(this.plugin, entityLocation, scheduledTask -> {
                entityLocation.getBlock().setType(Material.AIR);
                webBlocks.remove(entityLocation.getBlock());
            }, 5 * 20);
        } else {
            setWebBlocks(hitEntity);
        }

        arrow.remove();
    }

    private void setWebBlocks(Entity hitEntity) {
        this.plugin.getServer().getRegionScheduler().execute(this.plugin, hitEntity.getLocation(), () -> {
            for (Block block : getCube(hitEntity.getLocation())) {

                block.setType(Material.COBWEB);
                this.webBlocks.add(block);

                this.plugin.getServer().getRegionScheduler().runDelayed(this.plugin, block.getLocation(), scheduledTask -> {
                    if (block.getType() == Material.COBWEB) {
                        block.setType(Material.AIR);
                        webBlocks.remove(block);
                    }
                }, 5 * 20);
            }
        });
    }

    // Sticky Shot End!
    */
    private List<Block> getCube(Location start) {
        List<Block> newBlocks = new ArrayList<>();

        for (double x = start.getX() - 1; x <= start.getX() + 1; x++) {
            for (double z = start.getZ() - 1; z <= start.getZ() + 1; z++) {
                Location loc = new Location(start.getWorld(), x, start.getY(), z);
                if (loc.getBlock().getType() == Material.AIR) newBlocks.add(loc.getBlock());
            }
        }

        return newBlocks;
    }
}