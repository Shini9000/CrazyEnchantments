package com.badbones69.crazyenchantments.paper.api.managers;

import com.badbones69.crazyenchantments.paper.api.enums.CEnchantments;
import com.badbones69.crazyenchantments.paper.api.objects.TridentEnchantment;
import com.badbones69.crazyenchantments.paper.api.objects.PotionEffects;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class TridentEnchantmentManager {

    private final List<TridentEnchantment> tridentEnchantments = new ArrayList<>();

    public void load() {
        this.tridentEnchantments.clear();

        if (CEnchantments.BRINE.isActivated()) this.tridentEnchantments.add(new TridentEnchantment(CEnchantments.BRINE, List.of(new PotionEffects(PotionEffectType.POISON, 2 * 20, -1)), true));
    }
    
    public List<TridentEnchantment> getTridentEnchantments() {
        return this.tridentEnchantments;
    }
}