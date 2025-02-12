package com.badbones69.crazyenchantments.paper.api.objects;

import com.badbones69.crazyenchantments.paper.api.enums.CEnchantments;
import org.bukkit.entity.Trident;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public record EnchantedTrident(Trident thrownTrident, ItemStack heldTrident, Map<CEnchantment, Integer> enchantments) {

    public Entity getShooter() {
        return (Entity) this.thrownTrident.getShooter();
    }

    public int getLevel(CEnchantments enchantment) {
        return this.enchantments.get(enchantment.getEnchantment());
    }

    public boolean hasEnchantment(CEnchantment enchantment) {
        return this.enchantments.containsKey(enchantment);
    }

    public boolean hasEnchantment(CEnchantments enchantment) {
        return hasEnchantment(enchantment.getEnchantment());
    }

    public Map<CEnchantment, Integer> getEnchantments() {
        return this.enchantments;
    }

    public Trident getTrident() {
        return this.thrownTrident;
    }
}